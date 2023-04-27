<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
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
            <form name="searchForm" action="search" method="POST">
                <input type="radio" name="chk_info" id="human" value="human"checked>보낸 사람
                <input type="radio" name="chk_info" id="contents" value="contents" >제목
                <br>
                <input type="text" name="searchWord" placeholder="검색어 입력" value="${searchWord}">&nbsp;<input type="submit" value="검색" name="search"/>
            </form>
        </center> 
        <br>
        ${searchList}
    </div>

    <%@include file="footer.jspf"%>
    <script>
        function toggleRadio() {
            var radio1 = document.getElementById("human");
            var radio2 = document.getElementById("contents");
            if (${chk_info}.equals("human")) {
                radio1.checked = true;
                radio2.checked = false;
            } else {
                radio2.checked = true;
                radio1.checked = false;
            }
        }
    </script>
</body>
</html>
