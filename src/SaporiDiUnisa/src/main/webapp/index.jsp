<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sapori di Unisa</title>
        <link rel="stylesheet" href="style/index.css">
        <link rel="stylesheet" href="style/style.css">
        <link rel="apple-touch-icon" sizes="180x180" href="img/favicon/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="img/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="img/favicon/favicon-16x16.png">
        <link rel="manifest" href="img/favicon/site.webmanifest">
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