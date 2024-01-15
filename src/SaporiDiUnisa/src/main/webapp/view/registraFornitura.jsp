<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.7.1.slim.js" integrity="sha256-UgvvN8vBkgO0luPSUl2s8TIlOSYRoGFAX4jlCIm9Adc=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/registraFornitura.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer.css">
    <title>Sapori di Unisa - Registra Fornitura</title>
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
    <main>
    <div id="container-left">
        <h1>Inserisci lotto</h1>
        <!-- Questo form verrà invocato con AJAX -->
        <form action="${pageContext.request.contextPath}/AggiungiLotto" method="post">
            <!-- Il campo "nome" diventerà una lista dove saranno elencati tutti i prodotti del supermercato -->
            <label for="nome">Nome</label>
            <input type="text" name="nome" id="nome" size="255" required>
            <br>
            <label for="marchio">Marchio</label>
            <input type="text" name="marchio" id="marchio" size="255" required>
            <br>
            <label for="prezzo">Prezzo cad.</label>
            <input type="number" name="prezzo" id="prezzo" min="0" value="0" step="0.01" required>
            <br>
            <label for="quantita">Quantità</label>
            <input type="number" name="quantita" id="quantita" min="0" value="0" step="1" required>
            <br>
            <label for="dataScadenza">Data scadenza</label>
            <input type="date" name="dataScadenza" id="dataScadenza" required>
            <label for="foto">Foto</label>
            <input type="file" name="foto" id="foto" required>
            <br>
            <input type="submit" value="Aggiungi lotto">
        </form>
        <br>
    </div>
    <div id="container-right">
        <h1>Lotti inseriti</h1>
        <table id="lottiInseriti">
            <tr>
                <th>Nome</th>
                <th>Marchio</th>
                <th>Prezzo cad.</th>
                <th>Quantità</th>
                <th>Data scadenza</th>
                <th>Foto</th>
            </tr>
        </table>
    </div>
    </main>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
