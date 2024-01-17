<%@ page import="it.unisa.saporidiunisa.model.entity.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sapori Di Unisa - Mostra Prodotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<div class = "container-table">
    <h2>Prodotti</h2>

    <%
        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) session.getAttribute("prodotti");
        if (prodotti!= null && (!prodotti.isEmpty()))
        {
            %>
    <h3>Risultati:</h3>
    <table id = "product-table">
        <thead>
        <tr>
            <th>Prodotto</th>
            <th>Nome</th>
            <th>Marchio</th>
            <th>Prezzo Base</th>
            <th>Percentuale Sconto</th>
            <th>Prezzo Scontato</th>
            <th>Imposta Sconto</th>
        </tr>
        </thead>
        <tbody>
        <% for (Prodotto p : prodotti) {
            String foto = Base64.encodeBase64String(p.getFoto());%>
        <tr>
            <td><img src="data:image/jpeg;base64,<%=foto %>" alt="foto"></td>
            <td><%= p.getNome() %></td>
            <td><%=p.getMarchio()%></td>
            <td><%= p.getPrezzo() %>&euro;</td>
            <% if(p.isSconto()){ %>
                <td><%=(int)(((p.getPrezzo()-p.getPrezzoScontato())/p.getPrezzo())*100)%> %</td>
                <td><%=p.getPrezzoScontato()%></td>
                <td> </td>
            <%}else{%>
                <td>&#10060;</td>
                <td>&#10060;</td>
                <td>
                    <form action="${pageContext.request.contextPath}/mostra-prodotto-finanze-servlet" method="post" onclick="this.submit()">
                        <p id="confirm-button">Imposta Sconto</p>
                        <input type="hidden" name="prodotto" value="<%=p.getId()%>">
                    </form>
                </td>
        </tr>
        <% }} %>
        </tbody>
    </table>
    <%
    }else{ %>
    <p>Nessuna Prodotto</p>
    <%
        } %>

    <div id = "buttons-container">
        <a href="${pageContext.request.contextPath}/view/select/finanze.jsp"><button id="cancel-button">Indietro</button></a>
    </div>
</div>
<%@ include file="/WEB-INF/include/footer.html" %>

</body>
</html>
