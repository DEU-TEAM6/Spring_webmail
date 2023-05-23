<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>스팸메일함</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>
                function insertkeyword(){
                    retrun "insertkeyword";
                }
        </script>
    </head>
    <body>
        <%@include file="header.jspf"%>

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>


        <div id="main">
            <center>
                <form name="searchForm" action="search" method="POST">
                    <input type="radio" name="chk_info" value="human" checked>보낸 사람
                    <input type="radio" name="chk_info" value="contents" >제목
                    <br>
                    <input type="text" name="searchWord" placeholder="검색어 입력">&nbsp;<input type="submit" value="검색" name="search"/>
                </form>
            </center> 
            
                <input type="button" value="키워드 관리" onclick="location.href='insertkeyword'">
            
            <br>
            
            ${messageList}
        </div>

        <%@include file="footer.jspf"%>
    </body>
</html>
