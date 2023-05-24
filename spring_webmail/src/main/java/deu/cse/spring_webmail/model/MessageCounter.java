/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import jakarta.mail.Message;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class MessageCounter {

    public int getMessageCount(Message[] messages, int n, String userid) {
        StringBuilder buffer = new StringBuilder();
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        Addkeyword addkey = new Addkeyword(url, id, pw, driver);
        List<AddkeywordRow> keyword = addkey.getAllRows(userid);
        List<String> keywordStrings = new ArrayList<>();
        for (AddkeywordRow row : keyword) {
            // AddkeywordRow 객체의 필드를 사용하여 원하는 문자열 표현 생성
            keywordStrings.add(row.getKeyword());
        }
        String joinedKeywords = String.join(" ", keywordStrings);
        int count = 1;
        for (int i = messages.length - 1; i >= 0; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            boolean check = false;
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
            for (int j = 0; j < keywordStrings.size(); j++) {
                if (parser.getSubject().contains(keywordStrings.get(j))) {// || addkey.GetBody(userid).contains(keywordStrings.get(j))
                    check = true;
                }
                if (parser.getFromAddress().equals(userid)) {
                    check = false;
                }
            }
            if (n == 0 && check == false) { // 모두
                count++;
            } else if (n == 1) { //내게 쓴 메일함
                if (parser.getFromAddress().equals(userid)) {
                    count++;
                }
            } else if (n == 2) { //스팸메일함
                if (parser.getFromAddress().equals(userid) == false) { //parser.getSubject().contains(joinedKeywords)
                    if (check) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
