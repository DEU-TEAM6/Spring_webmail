<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="header.jspf"%>
        <br /><br />
        <span style="color:blue">비밀번호는 영어, 숫자, 특수문자 조합이며, 8자 이상으로 작성하세요.</span> <br /><br />
        <form name="register_form" action="register.do" method="POST">
            <table border="0" margin="auto" text-align="center">
                <tr>
                    <td>사용자 ID</td>
                    <td> <input type="text" name="id" value="" size="20" />  </td>
                </tr>
                <tr>
                    <td>암호 </td>
                    <td> <input type="password" name="password" value="" size="20"/> </td>
                </tr>
                <tr>
                    <td>암호 확인 </td>
                    <td> <input type="password" name="passwordcheck" value="" size="20"/> </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="회원가입" name="register" />
                        <input type="reset" value="초기화" name="reset" />
                        <a href ="login.do?menu=92"><input type="button" value="취소" name="cancel"></a>
                    </td>
                </tr>
            </table>
           <%@include file="footer.jspf"%>
    </body>
</html>
