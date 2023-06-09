/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        try {
            Class.forName(driver);
            String sql = "SELECT * FROM ADDRBOOK WHERE USERID= ?";
            try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String adduser = rs.getString("USERNAME");
                        String note = rs.getString("NOTE");
                        dataList.add(new AddrBookRow(userid, adduser, note));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }

        return dataList;
    }

    public boolean insertaddrbook(String userid, String adduser, String note) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ADDRBOOK VALUES(?,?,?)")) {
            pstmt.setString(1, userid);
            pstmt.setString(2, adduser);
            pstmt.setString(3, note);
            pstmt.executeUpdate();
            result = true;
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }

    public boolean deleteaddrbook(String userid, String address) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ADDRBOOK WHERE USERID=? AND USERNAME=?")) {
            pstmt.setString(1, userid);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
            result = true;
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }

    public boolean searchuser(String address) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM USERS"); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                if (rs.getString("USERNAME").equals(address)) {
                    result = true;
                    break;
                }
            }
            return result;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
            return result;
        }
    }
}
