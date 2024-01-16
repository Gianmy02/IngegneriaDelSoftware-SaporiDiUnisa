<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sapori Di Unisa - Regsitra Vendita</title>
    <script src="https://code.jquery.com/jquery-3.7.1.slim.js" integrity="sha256-UgvvN8vBkgO0luPSUl2s8TIlOSYRoGFAX4jlCIm9Adc=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/calculateTotal.js" defer></script>
    <script src="${pageContext.request.contextPath}/script/confirmSale.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
</head>
<body>


<%@ include file="/WEB-INF/include/header.html" %>


<%
    // Definisci una lista di oggetti con attributi (nome, azienda, prezzo, quantità)
    ArrayList<Esposizione> prodotti = (ArrayList<Esposizione>) session.getAttribute("prodottiEsposti"); // Supponiamo che tu abbia un metodo che restituisce la lista di prodotti
%>
<div class = "container-table">
    <h2>Nuova Vendita</h2>
    <table id = "product-table">
        <thead>
        <tr>
            <th>Prodotto</th>
            <th>Nome</th>
            <th>Azienda</th>
            <th>Prezzo</th>
            <th>Quantità</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Itera sulla lista di oggetti Item
            for (Esposizione e : prodotti) {
                String foto = Base64.encodeBase64String(e.getProdotto().getFoto());

        %>

            <tr class = "vendita-item" id="<%=e.getProdotto().getId()%>">
                <td><img src="data:image/jpeg;base64,<%= foto %>" alt="foto"></td>
                <td><%=e.getProdotto().getNome()%></td>
                <td><%=e.getProdotto().getMarchio()%></td>
                <td>
                    <select class = "item-quantity" onchange="calculateTotal()">
                        <!-- Utilizza una select per la quantità -->
                        <%
                            int quantita = e.getQuantita();
                            if (e.getQuantita() > 25) {
                                quantita = 25;
                            }

                            for (int i = 0; i <= quantita; i++){
                        %>
                        <option value=<%=i%>><%=i%></option>
                        <%}%>
                    </select>

                </td>
                <td><p class = "item-price"><%=e.getProdotto().getPrezzo()%> &euro;</p></td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <div id="total-container">
        <h2>Totale: <span id="total-amount">0</span> &euro;</h2>
    </div>
    <div id = "buttons-container">
        <button id="cancel-button"><a href="${pageContext.request.contextPath}/view/select_Cassiere.jsp">Annulla</a></button>
        <button id="confirm-button" onclick="confirmSale()">Conferma</button>
    </div>
    <%@ include file="/WEB-INF/include/footer.html" %>
</div>

</body>

</html>
