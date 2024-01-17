<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Sapori di Unisa - Errore</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/error.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/include/header.html" %>
        <main>
            <div class="error-message">
                <div class="error-symbol">&#9888;</div> <!-- Puoi sostituire questo simbolo con quello desiderato -->
                <p>ERRORE: <%= request.getAttribute("message") %></p>
                <button onclick="window.history.back()" class ="back-button">Torna indietro</button>
            </div>
        </main>
        <%@ include file="/WEB-INF/include/footer.html" %>
    </body>
</html>