<%-- 
    Document   : addrbook
    Created on : 2023. 5. 17., 오후 2:20:40
    Author     : kkll3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<!-- 제어기에서 처리하면 로직 관련 소스 코드 제거 가능!
<jsp:useBean id="pop3" scope="page" class="deu.cse.spring_webmail.model.Pop3Agent" />
<%
    pop3.setHost((String) session.getAttribute("host"));
    pop3.setUserid((String) session.getAttribute("userid"));
    pop3.setPassword((String) session.getAttribute("password"));
%>
-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 추가폼</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>

        </script>
    </head>
    <body>
        <%@include file="header.jspf"%>

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>


        <!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
        <div id="main">
            <form action="insert_address.do" method="POST">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>이름</td><!-- comment -->
                            <td><input type="text" name="name" size="20" /></td>
                        </tr>
                    <td>비고</td><!-- comment -->
                    <td><input type="text" name="note" value="" size="20" placeholder="추가할 사용자에대한 메모를 입력해주세요."/></td><!-- comment -->
                    </tr>
                    <tr>
                        <td colspan="2">
                    <center>
                        <input type="submit" value="추가" /> <input type="reset" value="초기화"/>
                    </center>
                    </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <br>
        </div>

        <%@include file="footer.jspf"%>

    </body>
</html>

