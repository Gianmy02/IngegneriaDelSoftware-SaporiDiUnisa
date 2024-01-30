<%@ page import="it.unisa.saporidiunisa.model.entity.Dipendente" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sapori di Unisa - Operazione effettuata</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/resultPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/success.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
    <%@ include file="include/header.html" %>
    <main>
        <div class="success-message">
            <div class="success-symbol">
                <span class="material-symbols-outlined">verified</span>
            </div>
            <p><%= request.getAttribute("message") %></p>
            <%
                Dipendente d = (Dipendente) session.getAttribute("dipendente");
                String address;
                if(d.getRuolo()== Dipendente.Ruolo.FINANZE)
                    address = "view/select/finanze.jsp";
                else if (d.getRuolo()== Dipendente.Ruolo.ADMIN)
                    address = "view/select/admin.jsp";
                else if (d.getRuolo()== Dipendente.Ruolo.CASSIERE)
                    address = "view/select/cassiere.jsp";
                else
                    address = "view/select/magazziniere.jsp";
            %>
            <a href="${pageContext.request.contextPath}/<%=address%>"><button class ="back-button">Torna alla Pagina Iniziale</button></a>
        </div>
    </main>
    <%@ include file="include/footer.html" %>
</body>
</html>
