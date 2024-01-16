<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Lotto" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sapori di Unisa - Aggiunta Scaffale</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
        <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    </head>
    <body>

        <%
            ArrayList<Lotto> lottiMagazzino = (ArrayList<Lotto>) request.getAttribute("lottiMagazzino");
            ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
        %>

        <form action="${pageContext.request.contextPath}/AggiungiScaffale" method="post">

        <div class="container">
            <table>
                <tr>
                    <th>Prodotti Scaffale</th>
                </tr>
                <tr>
                    <th>Nome</th>
                    <th>Azienda</th>
                    <th>Data Scadenza</th>
                    <th>Qnt attuale</th>
                    <th>Qnt da aggiungere</th>
                </tr>

        <%
            for(Esposizione e : lottiScaffale)
            {
                int qntMax = (e.getLotto().getQuantitaAttuale() - e.getQuantita());
        %>

                <tr>
                <td><%=e.getProdotto().getNome()%></td>
                <td><%=e.getProdotto().getMarchio()%></td>
                <td><%=e.getLotto().getDataScadenza()%></td>
                <td><%=e.getQuantita()%></td>
                <td><input type="number" name="qntScaffale<%=e.getLotto().getId()%>" min="0" max="<%=qntMax%>" value="0"></td>
                </tr>

        <%
            }
        %>

            </table>
        </div>

        <div class="container">
            <table>
                <tr>
                    <th>Prodotti Magazzino</th>
                </tr>
                <tr>
                    <th>Nome</th>
                    <th>Azienda</th>
                    <th>Data Scadenza</th>
                    <th>Qnt attuale</th>
                    <th>Qnt da aggiungere</th>
                </tr>

                <%
                    for(Lotto l: lottiMagazzino)
                    {
                        int qntMax = l.getQuantitaAttuale();
                %>

                <tr>
                    <td><%=l.getProdotto().getNome()%> </td>
                    <td><%=l.getProdotto().getMarchio()%> </td>
                    <td><%=l.getDataScadenza()%> </td>
                    <td><%=l.getQuantitaAttuale()%> </td>
                    <td><input type="number" name="qntMagazzino<%=l.getId()%>" min="0" max="<%=qntMax%>" value="0"></td>
                </tr>

                <%
                    }
                %>
            </table>
        </div>

            <input id="confirm-button" type="submit" value="CONFERMA">

        </form>

    </body>
</html>
