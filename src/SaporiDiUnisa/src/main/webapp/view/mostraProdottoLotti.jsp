<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Prodotto" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Lotto" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sapori Di Unisa - Magazzino</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
<%      HashMap<Prodotto, ArrayList<Lotto>> magazzino = (HashMap<Prodotto, ArrayList<Lotto>>) session.getAttribute("magazzino");
        if(!magazzino.isEmpty() && magazzino!=null){%>
<h2>Magazzino Prodotti</h2>
<div class = "container-table">
    <table id = "product-table">
        <thead>
        <tr>
            <th>Prodotto</th>
            <th>Nome</th>
            <th>Marchio</th>
            <th>Quantità Lotti</th>
            <th>Costo Prodotto Base</th>
            <th>Visualizza Lotti</th>
        </tr>
        </thead>
        <tbody>
            <%for (Prodotto prodotto : magazzino.keySet()) {
                String foto = Base64.encodeBase64String(prodotto.getFoto());
                %>
            <tr>
                <td><img src="data:image/jpeg;base64,<%=foto %>" alt="foto"></td>
                <td><%= prodotto.getNome() %></td>
                <td><%=prodotto.getMarchio()%></td>
                <td><%=magazzino.get(prodotto).size()%></td>
                <td><%=prodotto.getPrezzo()%> &euro;</td>
                <td><form action="${pageContext.request.contextPath}/MostraLottiProdotto" method="post" onclick="this.submit()">
                    <p id="confirm-button">Visualizza Lotti</p>
                    <input type="hidden" name="prodotto" value="<%=prodotto.getId()%>">
                </form></td>
            </tr>
            <%}%>
        </tbody>
    </table>
<%}else{%>
<p>Magazzino Vuoto</p>
<%}%>
<div id = "buttons-container">
    <a href="${pageContext.request.contextPath}/view/select_admin.jsp"><button id="cancel-button">Indietro</button></a>
</div>
</div>

<%@ include file="/WEB-INF/include/footer.html" %>

</body>
</html>
