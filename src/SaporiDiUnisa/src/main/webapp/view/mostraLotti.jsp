<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Prodotto" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Lotto" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Dipendente" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sapori di Unisa - Mostra Lotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<main>

    <%
        HashMap<Prodotto, ArrayList<Lotto>> magazzino = (HashMap<Prodotto, ArrayList<Lotto>>) session.getAttribute("magazzino");
        Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
        ArrayList<Lotto> lotti = magazzino.get(prodotto);
        Dipendente dipendente = (Dipendente)session.getAttribute("dipendente");
    %>


        <div class="container-table">
            <form id="formRimozione" action="${pageContext.request.contextPath}/EliminaLotto" method="post">
            <table>
                <tr>
                    <th>Lotti di <%=prodotto.getNome()%></th>
                </tr>
                <tr>
                    <th>ID</th>
                    <th>Giorno Fornitura</th>
                    <th>Costo</th>
                    <th>Data Scadenza</th>
                    <th>Quantità attuale</th>
                    <%
                        if(dipendente.getRuolo() == Dipendente.Ruolo.ADMIN)
                        {
                    %>
                    <th>Elimina Lotto</th>
                    <%
                        }
                    %>
                </tr>

                <%
                    for(Lotto l : lotti)
                    {
                %>

                <tr>
                    <td><%=l.getId()%></td>
                    <td><%=l.getFornitura().getGiorno()%></td>
                    <td><%=l.getCosto()%></td>
                    <td><%=l.getDataScadenza()%></td>
                    <td><%=l.getQuantitaAttuale()%></td>

                <%
                    if(dipendente.getRuolo() == Dipendente.Ruolo.ADMIN)
                    {
                %>
                    <input type="hidden" name="lotto" value="<%=l.getId()%>">
                    <td><button type="submit">Elimina</button></td>
                <%
                    }
                %>
                </tr>

                <%
                    }
                %>
            </table>
    </form>
            <div id = "buttons-container">
                <a href="${pageContext.request.contextPath}/view/mostraProdottoLotti.jsp"><button id="cancel-button">Indietro</button></a>
            </div>
        </div>
</main>
</body>
</html>
