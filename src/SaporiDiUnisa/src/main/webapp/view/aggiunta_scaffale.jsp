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
    </head>
    <body>
        <%@ include file="/WEB-INF/include/header.html" %>


        <%
            ArrayList<Lotto> lottiMagazzino = (ArrayList<Lotto>) request.getAttribute("lottiMagazzino");
            ArrayList<Esposizione> lottiScaffale = (ArrayList<Esposizione>) request.getAttribute("lottiScaffale");
        %>




        <%@ include file="/WEB-INF/include/footer.html" %>
    </body>
</html>
