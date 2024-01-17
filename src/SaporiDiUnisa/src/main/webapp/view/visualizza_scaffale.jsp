<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sapori di Unisa - Visualizza Scaffale</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/scaffale.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/eliminaScaduti.js" defer></script>
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>

<main>

<%
    ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
    ArrayList<Esposizione> lottiScaffaleScaduti = (ArrayList<Esposizione>) request.getAttribute("lottiScaffaleScaduti");
%>

<form id="formRimozione" action="${pageContext.request.contextPath}/EliminaScaduti" method="post">

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
            </tr>

            <%
                for(Esposizione e : lottiScaffale)
                {
                    boolean scaduto = false;
                    int l = e.getLotto().getId();
                    for(Esposizione e2 : lottiScaffaleScaduti){
                        if(e2.getLotto().getId() == l && !scaduto){
                            scaduto = true;
            %>

            <tr class="rigaScaduto" style="background-color: red">
                <td class="nomeProdotto"><%=e.getProdotto().getNome()%></td>
                <td class="azienda"><%=e.getProdotto().getMarchio()%></td>
                <td class="scadenza"><%=e.getLotto().getDataScadenza()%></td>
                <td class="quantita"><%=e.getQuantita()%></td>
            </tr>

            <%
                        }
                    }
                    if(!scaduto){
            %>

            <tr>
                <td><%=e.getProdotto().getNome()%></td>
                <td><%=e.getProdotto().getMarchio()%></td>
                <td><%=e.getLotto().getDataScadenza()%></td>
                <td><%=e.getQuantita()%></td>
            </tr>

            <%
                    }
                }
            %>

        </table>
    </div>

    <input class="btn" id="cancel-button" type="submit" value="ELIMINA SCADUTI">

</form>
</main>
<a href="${pageContext.request.contextPath}/view/select_VisualizzaScaffale_AggiungiProdottiScaffale.jsp"><button class="btn" id="indietro-button">INDIETRO</button></a>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
