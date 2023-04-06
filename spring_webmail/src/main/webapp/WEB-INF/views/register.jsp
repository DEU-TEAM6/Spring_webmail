<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>

<!-- 
20183215 정현수 
2023.03.31
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script type="text/javascript" src="js/register.js"></script>
    </head>
    <body>
        <%@include file="header.jspf"%>
        <br /><br />
        <div class="center">
            <span style="color:blue">비밀번호는 최소 8자리에서 최대 16자리까지 숫자, 영문, 특수문자 각 1개 이상 포함</span> <br /><br />
        </div>
        <form name="register_form" action="register.do" method="POST" onsubmit="return checkPassword()">
            <table border="0">
                <tr>
                    <td>사용자 ID</td>
                    <td> <input type="text" name="id" value="" size="20" required/>  </td>
                </tr>
                <tr>
                    <td>암호 </td>
                    <td> <input type="password" name="password" value="" pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$" id="pw1" size="20" required/> </td>
                </tr>
                <tr>
                    <td>암호 확인 </td>
                    <td> 
                        <input type="password" name="passwordcheck"  pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$" value="" id="pw2" size="20" required/> 
                        <font id ="checkPw" size="2"></font>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="회원가입" name="register"/>
                        <input type="reset" value="초기화" name="reset" />
                        <a href ="login.do?menu=92"><input type="button" value="취소" name="cancel"></a>
                    </td>
                </tr>
            </table>
            <%@include file="footer.jspf"%>
    </body>
</html>
