<%@ page import="it.unisa.saporidiunisa.model.entity.Dipendente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sapori di Unisa - Modifica Pin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
<main>
    <form name="modificaPin" method="post" action="${pageContext.request.contextPath}/update-pin-servlet">
        <select name="ruolo">
            <option value="<%=Dipendente.Ruolo.ADMIN%>">admin</option>
            <option value="<%=Dipendente.Ruolo.CASSIERE%>">cassiere</option>
            <option value="<%=Dipendente.Ruolo.MAGAZZINIERE%>">magazziniere</option>
            <option value="<%=Dipendente.Ruolo.FINANZE%>">finanze</option>
        </select>
        <label for="newPin">NewPin</label><br>
        <input type="password" id="newPin" name="newPin" placeholder="newPin" pattern="[0-9]{4}" maxlength="4" title="Il PIN deve essere numerico e di massimo quattro cifre." required><br>
        <input type="submit" value="Modifica">
    </form>
</main>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
