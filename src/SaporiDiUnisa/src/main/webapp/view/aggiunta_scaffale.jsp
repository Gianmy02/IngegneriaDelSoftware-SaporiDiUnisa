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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/scaffale.css">
    </head>
    <body>

        <%
            ArrayList<Lotto> lottiMagazzino = (ArrayList<Lotto>) request.getAttribute("lottiMagazzino");
            ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
        %>

        <form action="${pageContext.request.contextPath}/AggiungiScaffale" method="post">

        <div class="table-container">
            <table class="tabella">
                <tr>
                    <th>Nome</th>
                    <th>Azienda</th>
                    <th>Data Scadenza</th>
                </tr>

        <%
            for(Esposizione e : lottiScaffale)
            {
                int qntMax = e.getLotto().getQuantitaAttuale();
                int qntMin = e.getQuantita();
        %>

            <tr>
            <td><%=e.getProdotto().getNome()%></td>
            <td><%=e.getProdotto().getMarchio()%></td>
            <td><%=e.getLotto().getDataScadenza()%></td>

            <td>
                <select name="quantitaEsposizione_<%=e.getLotto().getId()%>">

                <%
                    for(int i = qntMin; i <= qntMax; i++)
                    {
                %>
                <option value="<%=i%>"><%=i%></option>
                <%
                    }
                %>
                </select>
            </td>
            </tr>
            <%
                }
            %>
            </table>
        </div>
            <input type="submit" value="CONFERMA">
        </form>

        <table class="tabella">
            <tr>
                <th>Nome</th>
                <th>Azienda</th>
                <th>Data Scadenza</th>
                <th>Quantità</th>
            </tr>

        <%
            for(Lotto l: lottiMagazzino)
            {
        %>

        <tr>
            <td><%=l.getProdotto().getNome()%> </td>
            <td><%=l.getProdotto().getMarchio()%> </td>
            <td><%=l.getDataScadenza()%> </td>
            <td><%=l.getQuantitaAttuale()%> </td>
        </tr>
            <%
                }
            %>

        </table>



    </body>
</html>
