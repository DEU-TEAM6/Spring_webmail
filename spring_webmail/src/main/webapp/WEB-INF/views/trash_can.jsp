<%-- 
    Document   : trash_can
    Created on : 2023. 5. 2., 오후 6:31:21
    Author     : Jung-HyeonSu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>휴지통 화면</title>
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

        <table border="1">
            <thead>
                <tr>
                    <th>보낸 사람</th>
                    <th>제목</th>
                    <th>보낸 날짜</th>
                    <th>복원</th>
                    <th>영구삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" items="${trashcanList}">
                    <tr>
                        <td>${row.sender}</td>
                        <td>${row.title}</td>
                        <td>${row.last_updated}</td>
                        <td><a href="trashcan_mail.do?message_name=${row.message_name}&type=restore"> 복원 </a></td>
                        <td><a href="trashcan_mail.do?message_name=${row.message_name}&type=delete"onclick="return confirm('정말 삭제하시겠습니까?')">영구삭제</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%@include file="footer.jspf"%>
    </body>
</html>
