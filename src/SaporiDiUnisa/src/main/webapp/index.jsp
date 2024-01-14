<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Sapori di Unisa</title>
        <link rel="stylesheet" href="./style/index.css">
    </head>
    <body>
        <%@ include file="WEB-INF/include/header.html" %>

        <form name="login" method="post" action="${pageContext.request.contextPath}/login-servlet">
            <label for="pin">PIN</label>
            <input type="password" id="pin" name="pin">
            <input type="submit" value="Accedi">
        </form>

        <%@ include file="WEB-INF/include/footer.html" %>
    </body>
</html>