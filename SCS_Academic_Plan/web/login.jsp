<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Главная</title>
        <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/login.css" TYPE="text/css">
    </head>
    <body>
        <form action="/login" class="submit">
            <table class="login-table">
                <tr style="padding-bottom: 100px;">
                    <td>Вход в систему:</td>
                </tr>
                <tr>
                    <td>
                        <table class = "input-table">
                            <tr>
                                <td>Логин</td>
                                <td style="padding-left: 20px;"><input type="text" name="login" class="text-field"></td>
                            </tr>
                            <tr>
                                <td>Пароль</td>
                                <td style="padding-left: 20px;"><input type="password" name="password" class="text-field"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" name="submit" class="login-button" value="Войти"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>
