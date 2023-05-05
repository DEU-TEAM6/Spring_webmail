/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.sql.Blob;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
public class TrashCanRow {
    @Getter 
    private String message_name;
    @Getter
    private String repository_name;
    @Getter
    private String message_state;
    @Getter
    private String error_message;
    @Getter
    private String sender;
    @Getter
    private String recipients;
    @Getter
    private String remote_host;
    @Getter
    private String remote_addr;
    @Getter
    private Blob message_body;
    @Getter
    private Blob message_attributes;
    @Getter
    private Date last_updated;
}
