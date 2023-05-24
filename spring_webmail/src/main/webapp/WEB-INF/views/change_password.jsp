<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>
<!-- 
20183215 정현수 
2023.04.06
-->
<!DOCTYPE html>
<html lang="ko" xml:lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>비밀번호 변경 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script type="text/javascript" src="js/register.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>
        </script>
    </head>
    <body>
        <%@include file="header.jspf"%>
        <br /><br />
        <div class="center">
            <span style="color:blue">비밀번호는 최소 8자리에서 최대 16자리까지 숫자, 영문, 특수문자 각 1개 이상 포함</span> <br /><br />
        </div>
        <table border="0" summary="사용자 계정 생성 테이블">
            <form name="chagePw_form" action="changePw.do" method="POST" onsubmit="return checkPassword()">
                <tr>
                    <th id="사용자">사용자 ID</th>
                    <td> <input type="text" name="id" value="${userid}" size="20" readonly/>  </td>
                </tr>
                <tr>
                    <th id="기존암호">기존 암호 </th>
                    <td> <input type="password" name="oldpassword" value="" id="pw" size="20" required/> </td>
                </tr>
                <tr>
                    <th id="새암호">새암호 </th>
                    <td> <input type="password" name="password" value="" pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$" id="pw1" size="20" required/> </td>
                </tr>
                <tr>
                    <th id="새암호확인">새암호 확인 </th>
                    <td> 
                        <input type="password" name="passwordcheck"  pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$" value="" id="pw2" size="20" required/> 
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="변경" name="changePw"/>
                        <a href ="main_menu"><input type="button" value="취소" name="cancel"></a>
                    </td>
                </tr>
        </table>
        <%@include file="footer.jspf"%>
    </body>
</html>
