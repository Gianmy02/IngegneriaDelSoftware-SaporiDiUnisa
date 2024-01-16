<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Sapori di Unisa - Homepage Cassiere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/box.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
<main>
    <form action="${pageContext.request.contextPath}/MostraProdottiCassiereServlet" onclick="this.submit()">
        <span class="material-symbols-outlined">add</span>
        <p>Registra Vendita</p>
    </form>
    <form action="${pageContext.request.contextPath}/GoToServlet" method="post" onclick="this.submit()">
        <span class="material-symbols-outlined">news</span>
        <p>Visualizza Storico Vendite</p>
        <input type="hidden" name="address" value="visualizzaForniture">
    </form>
</main>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>