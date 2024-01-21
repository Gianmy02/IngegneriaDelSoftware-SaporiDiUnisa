<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sapori di Unisa - Errore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/resultPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/success.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
    <%@ include file="include/header.html" %>
    <main>
        <div class="success-message">
            <div class="success-symbol">
                <span class="material-symbols-outlined">verified</span>
            </div>
            <p><%= request.getAttribute("message") %></p>
            <button onclick="window.history.back()" class ="back-button">Torna indietro</button>
        </div>
    </main>
    <%@ include file="include/footer.html" %>
</body>
</html>
