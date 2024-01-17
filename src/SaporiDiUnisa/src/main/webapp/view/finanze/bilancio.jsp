<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="bilancio" scope="request" type="it.unisa.saporidiunisa.model.entity.Bilancio"/>
<!doctype html>
<html>
    <head>
        <title>Sapori di Unisa - Bilancio</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/finanze/bilancio.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/script/finanze/bilancio.js"></script>
    </head>
    <body>
        <div id="pie-chart-container">
            <canvas id="pie-chart"></canvas>
            <script type="text/javascript">drawPieChart(${bilancio.spese}, ${bilancio.incasso});</script>
        </div>
        <div id="perdite-guadagno">Perdite: ${bilancio.perdite}<br>Guadagno: ${bilancio.guadagno}</div>
        <div id="utile">Utile: ${bilancio.calculateUtile()}</div>
    </body>
</html>
