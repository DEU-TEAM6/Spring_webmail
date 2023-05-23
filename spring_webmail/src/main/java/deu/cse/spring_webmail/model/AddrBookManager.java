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
public class AddrBookManager {

    private String url;
    private String id;
    private String pw;
    private String driver;

    public AddrBookManager() {
        log.debug("Addkeyword(): Driver = {}", driver);
    }

    public AddrBookManager(String url, String id, String pw, String driver) {
        this.url = url;
        this.id = id;
        this.pw = pw;
        this.driver = driver;
        log.debug("Addkeyword(): Driver = {}", driver);
    }

    public List<AddrBookRow> getAllRows(String userid) {
        List<AddrBookRow> dataList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "SELECT * FROM ADDRBOOK WHERE USERID= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String adduser = rs.getString("USERNAME");
                String note = rs.getString("NOTE");
                dataList.add(new AddrBookRow(userid, adduser, note));
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

    public boolean insertaddrbook(String userid, String adduser, String note) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "INSERT INTO ADDRBOOK VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, adduser);
            pstmt.setString(3, note);
            if (pstmt != null) {
                pstmt.executeUpdate();
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            result = true;
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }

    public boolean deleteaddrbook(String userid, String address) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "DELETE FROM ADDRBOOK WHERE USERID=? AND USERNAME=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, address);
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
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }

    public boolean searchuser(String address) { // 입력한 ID가 USERS테이블에 있으면 TRUE반환
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "SELECT * FROM USERS";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("USERNAME").equals(address)) {
                    result = true;
                }
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
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }
}
