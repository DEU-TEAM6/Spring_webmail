<%-- 
    Document   : insertkeyword
    Created on : 2023. 5. 11., 오전 1:18:41
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
        <title>주메뉴 화면</title>
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
            <center>
                <form name="keywordForm" action="addkeyword.do" method="POST">
                    <br>
                    <input type="text" name="keyword" placeholder="키워드 입력">&nbsp;<input type="submit" value="키워드추가" name="addkeyword"/>
                </form>
            </center> 
            <table border="1">
                <thead>
                    <tr>
                        <th>키워드</th>
                        <th>영구삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${keywordlist}">
                        <tr>
                            <td>${row.keyword}</td>
                            <td><a href="deletekeyword.do?keyword=${row.keyword}&type=delete"> 영구삭제 </a></td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
            <br>
        </div>

        <%@include file="footer.jspf"%>
    </body>
</html>

