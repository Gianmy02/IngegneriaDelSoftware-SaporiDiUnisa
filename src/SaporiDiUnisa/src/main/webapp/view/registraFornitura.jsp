<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.7.1.slim.js" integrity="sha256-UgvvN8vBkgO0luPSUl2s8TIlOSYRoGFAX4jlCIm9Adc=" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/script/registraFornitura.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/registraFornitura.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/form.css">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/img/favicon/site.webmanifest">
    <title>Sapori di Unisa - Registra Fornitura</title>
</head>
<body>
<%@ include file="/WEB-INF/include/header.html" %>
    <main>
        <h1 id="title">Registra fornitura</h1>
        <div id="displayFlex-div">
            <div id="container-left" class="container-form">
                <h2>Inserisci lotto</h2>
                <!-- Questo form verrà invocato con AJAX -->
                <form action="${pageContext.request.contextPath}/AggiungiLotto" method="post">
                    <!-- Il campo "nome" diventerà una lista dove saranno elencati tutti i prodotti del supermercato -->
                    <div class="row">
                        <div class="col-50">
                            <label for="nome">Nome</label>
                        </div>
                        <div class="col-50">
                            <input type="text" name="nome" id="nome" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-50">
                            <label for="marchio">Marchio</label>
                        </div>
                        <div class="col-50">
                            <input type="text" name="marchio" id="marchio" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-50">
                            <label for="prezzo">Prezzo cad.</label>
                        </div>
                        <div class="col-50">
                            <input type="number" name="prezzo" id="prezzo" min="0" value="0" step="0.01" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-50">
                            <label for="quantita">Quantità</label>
                        </div>
                        <div class="col-50">
                            <input type="number" name="quantita" id="quantita" min="0" value="0" step="1" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-50">
                            <label for="dataScadenza">Data scadenza</label>
                        </div>
                        <div class="col-50">
                            <input type="date" name="dataScadenza" id="dataScadenza" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-50">
                            <label for="foto">Foto</label>
                        </div>
                        <div class="col-50">
                            <input type="file" name="foto" id="foto" required>
                        </div>
                    </div>
                    <input type="submit" value="Aggiungi lotto">
                </form>
            </div>
            <div id="container-right" class="container-form">
                <h2>Lotti inseriti</h2>
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
        </div>
    </main>
<%@ include file="/WEB-INF/include/footer.html" %>
</body>
</html>
