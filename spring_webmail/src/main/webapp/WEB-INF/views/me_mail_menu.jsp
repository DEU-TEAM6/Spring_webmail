<%-- 
    Document   : me_mail_menu
    Created on : 2023. 4. 6., 오전 11:03:31
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

<html lang="ko" xml:lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>내게쓴 메일 화면</title>
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

        <div id="main" style="text-align: center;">
            <form name="searchForm" action="search" method="POST">
                <input type="radio" name="chk_info" value="human"checked>보낸 사람
                <input type="radio" name="chk_info" value="contents" >제목
                <br>
                <input type="text" name="searchWord" placeholder="검색어 입력">&nbsp;<input type="submit" value="검색" name="search"/>
            </form>
            <br>
            ${meMessageList}
        </div>

        <%@include file="footer.jspf"%>
    </body>
</html>
