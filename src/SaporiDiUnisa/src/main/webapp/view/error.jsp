<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sapori Di Unisa - Error Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/error.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">

</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<%
    String message = (String) request.getAttribute("message");
%>
<main>

    <div class="error-message">
        <div class="error-symbol">&#9888;</div> <!-- Puoi sostituire questo simbolo con quello desiderato -->
        <p>ERRORE: <%=message%></p>
        <button onclick="goBack()" class ="back-button">Torna Indietro</button>

    </div>

</main>
    <%@ include file="/WEB-INF/include/footer.html" %>

</body>
</html>
<script>
    function goBack() {
        window.history.back();
    }
</script>