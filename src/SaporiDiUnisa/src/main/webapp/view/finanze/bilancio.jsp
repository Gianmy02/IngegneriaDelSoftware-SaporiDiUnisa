<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="bilancio" scope="request" type="it.unisa.saporidiunisa.model.entity.Bilancio"/>
<!doctype html>
<html lang="it">
<head>
    <title>Sapori di Unisa - Bilancio</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/finanze/bilancio.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/finanze/bilancio.js"></script>
</head>
<%@ include file="/WEB-INF/include/header.html" %>

<body>
<h2>Ricerca Bilancio</h2>

<form action="${pageContext.request.contextPath}/bilancio-periodo-servlet" method="post">
    <label for="inizio">Data Inizio:</label>
    <input type="date" id="inizio" name="inizio" max="<%=LocalDate.now().minusDays(1)%>" required>

    <label for="fine">Data Fine:</label>
    <input type="date" id="fine" name="fine" max="<%=LocalDate.now().minusDays(1)%>" required>

    <input id="confirm-button" type="submit" value="Invia">
</form>
<% if(request.getAttribute("dataInizio") != null && request.getAttribute("dataFine") !=null){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate dataInizio = (LocalDate) request.getAttribute("dataInizio");
    LocalDate dataFine = (LocalDate) request.getAttribute("dataFine");

    String inizio = dataInizio.format(formatter);
    String fine = dataFine.format(formatter); %>
<h2>Bilancio aggiornato nei giorni : <%=inizio%> e <%=fine%></h2>
<%}else{%>
<h2>Bilancio Totale</h2>

<%}%>
<div id="pie-chart-container">
    <canvas id="pie-chart"></canvas>
    <script type="text/javascript">drawPieChart(${bilancio.spese}, ${bilancio.incasso});</script>
</div>
<div id="perdite-guadagno">
    Guadagni: ${bilancio.guadagno} €<br>Perdite: ${bilancio.perdite} €
</div>
<div id="utile" class="${bilancio.calculateUtile() >= 0 ? 'positive' : 'negative'}">
    Utile: ${bilancio.calculateUtile()} €
</div>
<div>
    <a href="bilancio-servlet"><button id="bilancio-totale-buton">Bilancio Totale</button></a>
    <a href="${pageContext.request.contextPath}/view/select/finanze.jsp"><button id="cancel-button">Indietro</button></a>
</div>

</body>
</html>
