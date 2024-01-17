<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Venduto" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sapori Di Unisa - Mostra Storico Vendita</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<div class = "container-table">
    <h2>Ricerca Prodotti</h2>

    <form action="${pageContext.request.contextPath}/MostraStoricoVenditeServlet" method="post">
        <label>Data Inizio:</label>
        <input type="date" name="inizio" max="<%=LocalDate.now().minusDays(1)%>" required>

        <label>Data Fine:</label>
        <input type="date" name="fine" max="<%=LocalDate.now().minusDays(1)%>" required>

        <input id="confirm-button" type="submit" value="Invia">
    </form>
    <%
        ArrayList<Venduto> venduti = (ArrayList<Venduto>) session.getAttribute("prodotti");
        if (venduti!= null && (!venduti.isEmpty()))
        {
            if(request.getAttribute("dataInizio") != null && request.getAttribute("dataFine") !=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataInizio = (LocalDate) request.getAttribute("dataInizio");
                LocalDate dataFine = (LocalDate) request.getAttribute("dataFine");

                String inizio = dataInizio.format(formatter);
                String fine = dataFine.format(formatter);
    %>
    <h3>Risultati:</h3>
    <p>Data inizio inserita: <%=inizio%>   Data Fine inserita: <%=fine%></p>
    <table id = "product-table">
        <thead>
        <tr>
            <th>Prodotto</th>
            <th>Nome</th>
            <th>Marchio</th>
            <th>Quantità</th>
            <th>Incasso</th>
            <th>Guadagno</th>
        </tr>
        </thead>
        <tbody>
        <% for (Venduto v : venduti) {
            String foto = Base64.encodeBase64String(v.getProdotto().getFoto());%>
        <tr>
            <td><img src="data:image/jpeg;base64,<%=foto %>" alt="foto"></td>
            <td><%= v.getProdotto().getNome() %></td>
            <td><%=v.getProdotto().getMarchio()%></td>
            <td><%= v.getQuantita() %></td>
            <td><%=v.getCosto()%> &euro;</td>
            <td><%=v.getGuadagno()%> &euro;</td>
        </tr>
        <% } %>
        </tbody>
    </table>
            <% }
        }else{ %>
    <p>Nessuna Risultato, inserisci una data</p>
    <%
    } %>

    <div id = "buttons-container">
        <a href="${pageContext.request.contextPath}/view/select/select_Cassiere.jsp"><button id="cancel-button">Indietro</button></a>
    </div>
</div>
<%@ include file="/WEB-INF/include/footer.html" %>

</body>
</html>
