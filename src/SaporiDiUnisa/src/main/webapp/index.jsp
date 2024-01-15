<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Sapori di Unisa</title>
        <link rel="stylesheet" href="style/index.css">
        <link rel="stylesheet" href="style/header.css">
        <link rel="stylesheet" href="style/main.css">
        <link rel="stylesheet" href="style/footer.css">
    </head>
    <body>
        <%@ include file="WEB-INF/include/header.html" %>
        <main>
            <form name="login" method="post" action="${pageContext.request.contextPath}/login-servlet">
                <label for="pin">Login</label><br>
                <input type="password" id="pin" name="pin" placeholder="Pin" pattern="[0-9]{4}" maxlength="4" title="Il PIN deve essere numerico e di massimo quattro cifre." required><br>
                <input type="submit" value="Accedi">
            </form>
        </main>
        <%@ include file="WEB-INF/include/footer.html" %>
    </body>
</html>