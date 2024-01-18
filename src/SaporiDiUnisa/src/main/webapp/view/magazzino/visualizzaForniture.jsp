<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/magazzino/visualizzaForniture.js" defer></script>
    <title>Sapori di Unisa - Visualizza Forniture</title>
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
    <main>
        <h1>Lista forniture</h1>
        <table id="forniture-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Lotti</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </main>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
