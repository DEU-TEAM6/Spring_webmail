/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author PC
 */
@Slf4j
public class TrashCanManager {

    private String url;
    private String id;
    private String pw;
    private String driver;

    public TrashCanManager() {
        log.debug("TrashCanManager(): Driver = {}", driver);
    }

    public TrashCanManager(String url, String id, String pw, String driver) {
        this.url = url;
        this.id = id;
        this.pw = pw;
        this.driver = driver;
        log.debug("AddrBookManager(): Driver = {}", driver);
    }

    public List<TrashCanRow> getAllRows(String userid) {
        List<TrashCanRow> dataList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "SELECT * FROM TRASHCAN WHERE REPOSITORY_NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String message_name = rs.getString("message_name");
                String repository_name = rs.getString("repository_name");
                String message_state = rs.getString("message_state");
                String error_message = rs.getString("error_message");
                String sender = rs.getString("sender");
                sender = sender.substring(0, sender.indexOf("@"));
                String recipients = rs.getString("recipients");
                String remote_host = rs.getString("remote_host");
                String remote_addr = rs.getString("remote_addr");
                Blob message_body = rs.getBlob("message_body");
                Blob message_attributes = rs.getBlob("message_attributes");
                Date last_updated = rs.getDate("last_updated");
                dataList.add(new TrashCanRow(message_name, repository_name, message_state, error_message, sender, recipients,
                        remote_host, remote_addr, message_body, message_attributes, last_updated));
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

    public boolean deleteRow(String message_name) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "DELETE FROM TRASHCAN WHERE MESSAGE_NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, message_name);

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
            log.error("deleteRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return result;
    }

    public boolean insertRow(String message_name) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        List<Object> rowList = selectRow(message_name);
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "INSERT INTO INBOX VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < 11; i++) {
                Object obj = rowList.get(i);
                if (obj == null) {
                    switch (i) {
                        case 8:
                        case 9:
                            pstmt.setNull(i + 1, java.sql.Types.BLOB);
                            break;
                        case 10:
                            pstmt.setNull(i + 1, java.sql.Types.DATE);
                            break;
                        default:
                            pstmt.setNull(i + 1, java.sql.Types.VARCHAR);
                    }
                } else {
                    switch (i) {
                        case 8:
                        case 9:
                            pstmt.setBlob(i + 1, (Blob) obj);
                            break;
                        case 10:
                            pstmt.setDate(i + 1, (Date) obj);
                            break;
                        default:
                            pstmt.setString(i + 1, (String) obj);
                    }
                }
            }
            pstmt.executeUpdate();
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            deleteRow(message_name); // 휴지통에서 지우기
            result = true;
        } catch (Exception ex) {
            log.error("insertRow 오류가 발생했습니다. (발생 오류: {})", ex.getMessage());
        }
        return result;
    }

    public List<Object> selectRow(String message_name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object> row = new ArrayList<>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "SELECT * FROM TRASHCAN WHERE MESSAGE_NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, message_name);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                row.add(rs.getString("message_name"));
                    row.add(rs.getString("repository_name"));
                    row.add(rs.getString("message_state"));
                    row.add(rs.getString("error_message"));
                    row.add(rs.getString("sender"));
                    row.add(rs.getString("recipients"));
                    row.add(rs.getString("remote_host"));
                    row.add(rs.getString("remote_addr"));
                    row.add(rs.getBlob("message_body"));
                    row.add(rs.getBlob("message_attributes"));
                    row.add(rs.getDate("last_updated"));
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
        return row;
    }
}
