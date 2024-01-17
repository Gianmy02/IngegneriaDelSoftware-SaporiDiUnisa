<%@ page import="it.unisa.saporidiunisa.model.entity.Dipendente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sapori di Unisa - Modifica Pin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/modificaPin.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<main class="main-container">
    <form name="modificaPin" method="post" action="${pageContext.request.contextPath}/update-pin-servlet">
        <div class="form-group">
            <label for="ruolo">Seleziona il ruolo</label><br>
            <select id="ruolo" name="ruolo" class="form-control">
                <option value="<%=Dipendente.Ruolo.ADMIN%>">admin</option>
                <option value="<%=Dipendente.Ruolo.CASSIERE%>">cassiere</option>
                <option value="<%=Dipendente.Ruolo.MAGAZZINIERE%>">magazziniere</option>
                <option value="<%=Dipendente.Ruolo.FINANZE%>">finanze</option>
            </select>
        </div>
        <div class="form-group">
            <label for="newPin">Inserisci il nuovo PIN</label><br>
            <input type="password" id="newPin" name="newPin" class="form-control" placeholder="Inserisci nuovo PIN" pattern="[0-9]{4}" maxlength="4" title="Il PIN deve essere numerico e di massimo quattro cifre." required>
        </div>
        <input type="submit" value="Modifica" class="btn">
    </form>
</main>

<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
