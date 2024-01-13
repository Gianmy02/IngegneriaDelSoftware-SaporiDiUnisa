<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Sapori di Unisa</title>
    </head>
    <body>
        <form name="login" method="post" action="${pageContext.request.contextPath}/login-servlet">
            <label for="pin">PIN</label>
            <input type="password" id="pin" name="pin">
            <input type="submit" value="Accedi">
        </form>
    </body>
</html>
