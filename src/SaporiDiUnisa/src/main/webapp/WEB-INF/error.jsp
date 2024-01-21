<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="it">
    <head>
        <title>Sapori di Unisa - Errore</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/resultPage.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/error.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
        <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    </head>
    <body>
        <%@ include file="include/header.html" %>
        <main>
            <div class="error-message">
                <div class="error-symbol">&#9888;</div> <!-- Puoi sostituire questo simbolo con quello desiderato -->
                <p>ERRORE: <%= request.getAttribute("message") %></p>
                <button onclick="window.history.back()" class ="back-button">Torna indietro</button>
            </div>
        </main>
        <%@ include file="include/footer.html" %>
    </body>
</html>