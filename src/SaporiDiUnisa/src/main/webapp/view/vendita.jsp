<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sapori Di Unisa - Regsitra Vendita</title>
</head>
<body>




<%
    // Definisci una lista di oggetti con attributi (nome, azienda, prezzo, quantità)
    ArrayList<Esposizione> prodotti = (ArrayList<Esposizione>) session.getAttribute("prodottiEsposti"); // Supponiamo che tu abbia un metodo che restituisce la lista di prodotti
%>

<table border="1">
    <thead>
    <tr>
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
    %>
    <tr>
        <td><%=e.getProdotto().getNome()%></td>
        <td><%=e.getProdotto().getMarchio()%></td>
        <td><%=e.getProdotto().getPrezzo()%></td>
        <td>
            <select name="quantity">
            <!-- Utilizza una select per la quantità -->
            <%
                int quantita = e.getQuantita();
                if (e.getQuantita() > 20) {
                    quantita = 20;
                }

                for (int i = 0; i <= quantita; i++){
            %>
                <option value=<%=i%>><%=i%></option>
            <%}%>
            </select>

        </td>
    </tr>
    <% } %>
    </tbody>
</table>

</body>

</html>
