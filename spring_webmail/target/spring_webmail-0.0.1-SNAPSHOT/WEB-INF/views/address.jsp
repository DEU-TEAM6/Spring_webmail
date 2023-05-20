<%-- 
    Document   : address
    Created on : 2023. 5. 17., 오후 2:04:44
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
        <title>주소록 화면</title>
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
            <input type="button" value="주소록 추가" onclick="location.href = 'insert_address'">
            <br>
            <table border="1">
                <thead>
                    <tr>
                        <th>사용자</th>
                        <th>이메일</th>
                        <th>비고</th>
                        <th>사용자 삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${addrbooklist}">
                        <tr>
                            <td><a href="address_mail?adduser=${row.username}&type=show">${row.username}</a></td>
                            <td>${row.username}@loacal.host</td>
                             <td>${row.note}</td>
                            <td><a href="deleteaddress.do?adduser=${row.username}&type=delete"> 영구삭제 </a></td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
        <%@include file="footer.jspf"%>
    </body>
</html>
