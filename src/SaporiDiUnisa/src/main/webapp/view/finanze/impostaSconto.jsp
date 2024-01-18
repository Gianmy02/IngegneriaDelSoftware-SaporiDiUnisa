<%@ page import="it.unisa.saporidiunisa.model.entity.Prodotto" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/finanze/sconto.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/finanze/updatePercentage.js"></script>
    <title>Sapori Di Unisa - Imposta Sconto</title>
</head>
<body>
    <main>
    <%@ include file="/WEB-INF/include/header.html" %>
<%
        Prodotto p = (Prodotto) session.getAttribute("prodottoSelezionato");
        if(p!=null){
            String foto = Base64.encodeBase64String(p.getFoto());
%>
        <div class="content-wrapper">
            <div class="content-wrapper">
                <div class="product-details">
                    <img src="data:image/jpeg;base64,<%= foto %>" alt="Foto" class="product-image">
                    <p id="price">Prezzo Base: <%=p.getPrezzo()%>€</p>
                    <form action="${pageContext.request.contextPath}/imposta-sconto-servlet" method="post">
                        <input type="hidden" name="prodotto" value="<%=p.getId()%>">
                        <label for="discount">Sconto (%):</label>
                        <input type="number" id="discount" name="sconto" min="1" max="100" class="discount-input" oninput="udpatePercentage()" required>
                        <p class="discounted-price">Prezzo scontato: <span id="discountedPrice">0.00</span> €</p>
                        <label for="dataInizioSconto">Data Inizio:</label>
                        <input type="date" id="dataInizioSconto" name="dataInizioSconto" min = "<%=LocalDate.now().plusDays(1)%>" required>
                        <label for="dataFineSconto">Data Fine:</label>
                        <input type="date" id="dataFineSconto" name="dataFineSconto" min = "<%=LocalDate.now().plusDays(2)%>" required>
                        <button type="submit">Invia</button>
                    </form>
                    <div id = "buttons-container">
                        <a href="${pageContext.request.contextPath}/view/finanze/visualizzaProdotti.jsp"><button style="background-color: red" id="cancel-button">Indietro</button></a>
                    </div>
                </div>
            </div>
        </div>
        <%}else{%>
            <p>Nessun Prodotto Selezionato</p>
       <% }%>
    </main>
    <%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
