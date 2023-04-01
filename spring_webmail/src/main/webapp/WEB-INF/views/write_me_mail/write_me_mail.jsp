<%-- 
    Document   : write_me_mail
    Created on : 2023. 4. 1., 오후 10:58:12
    Author     : kkll3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_previous_menu.jsp" />
        </div>
        <h1>Hello World!</h1>
        <%@include file="../footer.jspf"%>
    </body>
</html>
