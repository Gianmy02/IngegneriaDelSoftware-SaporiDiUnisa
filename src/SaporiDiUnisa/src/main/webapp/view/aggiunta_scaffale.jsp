<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Lotto" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sapori di Unisa - Aggiunta Scaffale</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/include/header.html" %>


        <%
            ArrayList<Lotto> lottiMagazzino = (ArrayList<Lotto>) request.getAttribute("lottiMagazzino");
            ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
        %>

        <%
            for(Esposizione e : lottiScaffale)
            {
        %>

        <h2>Esposizione</h2>
        <div>
            <p> Nome: <%=e.getProdotto().getNome()%> </p>
            <p> Azienda: <%=e.getProdotto().getMarchio()%> </p>
            <p> Data Scadenza: <%=e.getLotto().getDataScadenza()%> </p>
            <p> Quantità: <%=e.getQuantita()%> </p>
        </div>

        <%
            }
        %>

        <%
            for(Lotto l: lottiMagazzino)
            {
        %>

        <h2>Magazzino</h2>
        <div>
            <p> Nome: <%=l.getProdotto().getNome()%> </p>
            <p> Azienda: <%=l.getProdotto().getMarchio()%> </p>
            <p> Data Scadenza: <%=l.getDataScadenza()%> </p>
            <p> Quantità: <%=l.getQuantitaAttuale()%> </p>
        </div>

        <%
            }
        %>


        <%@ include file="/WEB-INF/include/footer.html" %>
    </body>
</html>
