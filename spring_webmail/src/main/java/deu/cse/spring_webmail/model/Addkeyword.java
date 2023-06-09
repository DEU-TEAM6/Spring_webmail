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
 * @김무경
 *
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

        try {
            Class.forName(driver);
            String sql = "SELECT * FROM SPAMKEYWORD WHERE USERNAME = ?";
            try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String keyword = rs.getString("keyword");
                        dataList.add(new AddkeywordRow(keyword, userid));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return dataList;
    }

    public boolean insertkeyword(String userid, String keyword) {
        boolean result = false;

        try {
            Class.forName(driver);
            String sql = "INSERT INTO SPAMKEYWORD VALUES(?,?)";
            try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userid);
                pstmt.setString(2, keyword);
                pstmt.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }

        return result;
    }

    public boolean deletekeyword(String userid, String keyword) {
        boolean result = false;

        try {
            Class.forName(driver);
            String sql = "DELETE FROM SPAMKEYWORD WHERE USERNAME=? AND KEYWORD=?";
            try (Connection conn = DriverManager.getConnection(url, id, pw); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userid);
                pstmt.setString(2, keyword);
                pstmt.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }

        return result;
    }
}
