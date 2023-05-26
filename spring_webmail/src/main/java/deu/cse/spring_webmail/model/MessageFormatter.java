/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import jakarta.mail.Message;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

/**
 *
 * @author skylo
 */
@Slf4j
@RequiredArgsConstructor
public class MessageFormatter {

    @NonNull
    private String userid;  // 파일 임시 저장 디렉토리 생성에 필요
    private HttpServletRequest request = null;

    // 220612 LJM - added to implement REPLY
    @Getter
    private String sender;
    @Getter
    private String subject;
    @Getter
    private String body;

    public String getMessageTable(Message[] messages, int n, int startIndex, int endIndex) {
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

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start 
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

        MessageCounter counter = new MessageCounter();
        int count = counter.getMessageCount(messages, n, userid);
        for (int i = messages.length - 1; i >= 0; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            MessageParser parser1 = new MessageParser(messages[i], userid);
            parser1.parse(true);  // envelope 정보만 필요
            boolean check = false;
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
            for (int j = 0; j < keywordStrings.size(); j++) {
                if (parser.getSubject().contains(keywordStrings.get(j)) || parser1.getBody().contains(keywordStrings.get(j))) {// || addkey.GetBody(userid).contains(keywordStrings.get(j))
                    check = true;
                }
                if (parser.getFromAddress().equals(userid)) {
                    check = false;
                }
            }
            if (n == 0 && check == false) { // 모두
                count--;
                if (count >= startIndex && count < endIndex) {
                    buffer.append("<tr> "
                            + " <td id=no>" + (i + 1) + " </td> "
                            + " <td id=sender>" + parser.getFromAddress() + "</td>"
                            + " <td id=subject> "
                            + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                            + parser.getSubject() + "</a> </td>"
                            + " <td id=date>" + parser.getSentDate() + "</td>"
                            + " <td id=delete>"
                            + "<a href=delete_mail.do"
                            + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                            + " </tr>");
                }
            } else if (n == 1) { //내게 쓴 메일함
                if (parser.getFromAddress().equals(userid)) {
                    count--;
                    if (count >= startIndex && count < endIndex) {
                        buffer.append("<tr> "
                                + " <td id=no>" + (i + 1) + " </td> "
                                + " <td id=sender>" + parser.getFromAddress() + "</td>"
                                + " <td id=subject> "
                                + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                                + parser.getSubject() + "</a> </td>"
                                + " <td id=date>" + parser.getSentDate() + "</td>"
                                + " <td id=delete>"
                                + "<a href=delete_mail.do"
                                + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                                + " </tr>");
                    }
                }
            } else if (n == 2) { //스팸메일함
                if (parser.getFromAddress().equals(userid) == false) { //parser.getSubject().contains(joinedKeywords)
                    if (check) {
                        count--;
                        if (count >= startIndex && count < endIndex) {
                            buffer.append("<tr> "
                                    + " <td id=no>" + (i + 1) + " </td> "
                                    + " <td id=sender>" + parser.getFromAddress() + "</td>"
                                    + " <td id=subject> "
                                    + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                                    + parser.getSubject() + "</a> </td>"
                                    + " <td id=date>" + parser.getSentDate() + "</td>"
                                    + " <td id=delete>"
                                    + "<a href=delete_mail.do"
                                    + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                                    + " </tr>");
                        }
                    }
                }
            }
            if (count < startIndex) {
                break; // 페이지 범위를 초과하면 루프 종료
            }
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessageTable(Message[] messages, String chk_info, String searchWord) {
        StringBuilder buffer = new StringBuilder();

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

        for (int i = messages.length - 1; i >= 0; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기

            if (chk_info.equals("human")) { // 보낸 사람을 선택한 경우
                if (parser.getFromAddress().contains(searchWord)) { // 검색한 단어가 제목에 포함된 경우
                    buffer.append("<tr> "
                            + " <td id=no>" + (i + 1) + " </td> "
                            + " <td id=sender>" + parser.getFromAddress() + "</td>"
                            + " <td id=subject> "
                            + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                            + parser.getSubject() + "</a> </td>"
                            + " <td id=date>" + parser.getSentDate() + "</td>"
                            + " <td id=delete>"
                            + "<a href=delete_mail.do"
                            + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                            + " </tr>");
                }
            } else { // 제목을 선택한 경우
                if (parser.getSubject().contains(searchWord)) { // 검색한 단어가 제목에 포함된 경우
                    buffer.append("<tr> "
                            + " <td id=no>" + (i + 1) + " </td> "
                            + " <td id=sender>" + parser.getFromAddress() + "</td>"
                            + " <td id=subject> "
                            + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                            + parser.getSubject() + "</a> </td>"
                            + " <td id=date>" + parser.getSentDate() + "</td>"
                            + " <td id=delete>"
                            + "<a href=delete_mail.do"
                            + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                            + " </tr>");
                }
            }
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessageTable(Message[] messages, String adduser) {
        StringBuilder buffer = new StringBuilder();
        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");
        for (int i = messages.length - 1; i >= 0; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
            if (parser.getFromAddress().equals(adduser)) { // 주소록에 추가한 사용자 메일만 보기
                buffer.append("<tr> "
                        + " <td id=no>" + (i + 1) + " </td> "
                        + " <td id=sender>" + parser.getFromAddress() + "</td>"
                        + " <td id=subject> "
                        + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                        + parser.getSubject() + "</a> </td>"
                        + " <td id=date>" + parser.getSentDate() + "</td>"
                        + " <td id=delete>"
                        + "<a href=delete_mail.do"
                        + "?msgid=" + (i + 1) + " onclick=\"return confirm('휴지통으로 이동합니다. 정말 삭제하시겠습니까?');\"> 삭제 </a>" + "</td>"
                        + " </tr>");
            }
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessage(Message message) {
        StringBuilder buffer = new StringBuilder();

        // MessageParser parser = new MessageParser(message, userid);
        MessageParser parser = new MessageParser(message, userid, request);
        parser.parse(true);

        sender = parser.getFromAddress();
        subject = parser.getSubject();
        body = parser.getBody();

        buffer.append("보낸 사람: " + parser.getFromAddress() + " <br>");
        buffer.append("받은 사람: " + parser.getToAddress() + " <br>");
        buffer.append("Cc &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : " + parser.getCcAddress() + " <br>");
        buffer.append("보낸 날짜: " + parser.getSentDate() + " <br>");
        buffer.append("제 &nbsp;&nbsp;&nbsp;  목: " + parser.getSubject() + " <br> <hr>");

        buffer.append(parser.getBody());

        String attachedFile = parser.getFileName();
        if (attachedFile != null) {
            buffer.append("<br> <hr> 첨부파일: <a href=download"
                    + "?userid=" + this.userid
                    + "&filename=" + attachedFile.replaceAll(" ", "%20")
                    + " target=_top> " + attachedFile + "</a> <br>");
        }

        return buffer.toString();
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
