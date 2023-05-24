<%-- 
    Document   : search
    Created on : 2023.04.28
    Author     : Jung-HyeonSu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>


<html lang="ko" xml:lang="ko">
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


        <div id="main" style="text-align: center;">
            <form name="searchForm" action="search" method="POST">
                <input type="radio" name="chk_info" id="human" value="human"checked>보낸 사람
                <input type="radio" name="chk_info" id="contents" value="contents" >제목
                <br>
                <input type="text" name="searchWord" placeholder="검색어 입력" value="${searchWord}">&nbsp;<input type="submit" value="검색" name="search"/>
            </form>
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
