/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author kkll3
 */
@AllArgsConstructor
@Builder
public class AddrBookRow {

    @Getter
    private String userid;
    @Getter
    private String username;
    @Getter
    private String note;
}
