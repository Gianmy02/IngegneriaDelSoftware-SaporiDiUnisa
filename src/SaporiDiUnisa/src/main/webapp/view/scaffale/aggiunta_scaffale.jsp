<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Lotto" %>
<%@ page import="it.unisa.saporidiunisa.model.entity.Esposizione" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sapori di Unisa - Aggiunta Scaffale</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/scaffale.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/scaffale/aggiuntaScaffale.js" defer></script>
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
<main>

    <%
        ArrayList<Lotto> lottiMagazzino = (ArrayList<Lotto>) request.getAttribute("lottiMagazzino");
        ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
    %>

    <form id="formAggiunta" action="${pageContext.request.contextPath}/AggiungiScaffale" method="post">

        <div class="container">
            <% if (lottiScaffale.isEmpty()) { %>
            <h2>Scaffale Vuoto</h2>
            <%} else {%>
            <h2>Prodotti Scaffale</h2>
            <table>
                <tr>
                    <th>Prodotto</th>
                    <th>ID Lotto</th>
                    <th>Nome</th>
                    <th>Azienda</th>
                    <th>Data Scadenza</th>
                    <th>Qnt Attuale Scaffale</th>
                    <th>Qnt Disponibile Magazzino</th>
                    <th>Qnt Aggiunta</th>
                </tr>

                <%
                    for (Esposizione e : lottiScaffale) {
                        if(e.getLotto().getDataScadenza().isAfter(LocalDate.now())){
                            String foto = Base64.encodeBase64String(e.getProdotto().getFoto());
                            int qntMax = e.getLotto().getQuantitaAttuale();
                %>

                <tr class="RigaS">
                    <td><img src="data:image/jpeg;base64,<%= foto %>" alt="foto"></td>
                    <td class="idLottoS"><%=e.getLotto().getId()%></td>
                    <td class="nomeProdottoS"><%=e.getProdotto().getNome()%></td>
                    <td class="nomeAziendaS"><%=e.getProdotto().getMarchio()%></td>
                    <td class="dataScadenzaS"><%=e.getLotto().getDataScadenza()%></td>
                    <td class="qntAttualeS"><%=e.getQuantita()%></td>
                    <td class="qntAttualeS"><%=e.getLotto().getQuantitaAttuale()%></td>
                    <td><input type="number" name="qntScaffale<%=e.getLotto().getId()%>" min="0" max="<%=qntMax%>" value="0"></td>
                </tr>

                <%
                        }
                    }
                %>

            </table>
            <%}%>
        </div>

        <div class="container">
            <% if (lottiMagazzino.isEmpty()) { %>
            <h2>Magazzino Vuoto</h2>
            <%} else {%>
            <h2>Prodotti Magazzino</h2>
            <table>
                <tr>
                    <th>Prodotto</th>
                    <th>ID Lotto</th>
                    <th>Nome</th>
                    <th>Azienda</th>
                    <th>Data Scadenza</th>
                    <th>Qnt Attuale Magazzino</th>
                    <th>Qnt Aggiunta Scaffale</th>
                </tr>

                <%
                    for (Lotto l : lottiMagazzino) {
                        String foto = Base64.encodeBase64String(l.getProdotto().getFoto());

                        int qntMax = l.getQuantitaAttuale();
                %>

                <tr class="RigaM">
                    <td><img src="data:image/jpeg;base64,<%= foto %>" alt="foto"></td>
                    <td class="idLottoM"><%=l.getId()%></td>
                    <td class="nomeProdottoM"><%=l.getProdotto().getNome()%></td>
                    <td class="nomeAziendaM"><%=l.getProdotto().getMarchio()%></td>
                    <td class="dataScadenzaM"><%=l.getDataScadenza()%></td>
                    <td class="qntAttualeM"><%=l.getQuantitaAttuale()%></td>
                    <td><input type="number" name="qntMagazzino<%=l.getId()%>" min="0" max="<%=qntMax%>" value="0"></td>
                </tr>

                <%
                    }
                %>
            </table>
            <%}%>
        </div>

        <input class="btn" id="confirm-button" type="submit" value="CONFERMA">

    </form>

    <a href="${pageContext.request.contextPath}/view/select/select_VisualizzaScaffale_AggiungiProdottiScaffale.jsp">
        <button class="btn" id="indietro-button">INDIETRO</button>
    </a>

</main>
</body>
</html>