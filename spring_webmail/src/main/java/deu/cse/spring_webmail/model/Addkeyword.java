/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author kkll3
 */
@Slf4j
public class Addkeyword {

    private String url;
    private String id;
    private String pw;
    private String driver;

    public Addkeyword() {
        log.debug("Addkeyword(): Driver = {}", driver);
    }
    public Addkeyword(String url, String id, String pw, String driver) {
        this.url = url;
        this.id = id;
        this.pw = pw;
        this.driver = driver;
        log.debug("Addkeyword(): Driver = {}", driver);
    }
    
    public List<AddkeywordRow> getAllRows(String userid) {
        List<AddkeywordRow> dataList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "SELECT * FROM SPAMKEYWORD WHERE USERNAME= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String keyword = rs.getString("keyword");
                dataList.add(new AddkeywordRow(keyword,userid));
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception ex) {
            log.error("오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return dataList;
    }
    public boolean insertkeyword(String userid, String keyword) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "INSERT INTO SPAMKEYWORD VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, keyword);
            pstmt.executeUpdate();
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            result = true;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return result;
    }
    
    public boolean deletekeyword(String userid,String keyword){
        boolean result=false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "DELETE FROM SPAMKEYWORD WHERE USERNAME=? AND KEYWORD=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, keyword);
            pstmt.executeUpdate();
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            result = true;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return result;
    }
}
