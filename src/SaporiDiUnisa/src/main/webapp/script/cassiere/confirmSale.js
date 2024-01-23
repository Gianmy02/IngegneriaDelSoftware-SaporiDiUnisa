function confirmSale() {
    var saleData = [];
    var dettagli = []

    $(".vendita-item").each(function(index, element) {
        var productId = $(element).attr("id");
        var quantity = $(element).find(".item-quantity").val();
        var price = $(element).find(".item-price").text();
        var name = $(element).find(".product-name").text();
        price = parseFloat(price);
        if (quantity > 0) {
            saleData.push({
                productId: parseInt(productId), // Assicura che productId sia un numero intero
                quantity: parseInt(quantity), // Assicura che quantity sia un numero intero
                price: price
            });
            dettagli.push({
                quantity: parseInt(quantity), // Assicura che quantity sia un numero intero
                price: price,
                name: name
            });
        }
    });
    if (saleData.length === 0) {
        alert("Errore: Nessun prodotto con quantità maggiore di 0.");
        return;
    }
        var saleDetailsString = "Dettagli della vendita:\n";
        for (var i = 0; i < dettagli.length; i++) {
            saleDetailsString += "Prodotto : " + dettagli[i].name + ", Quantità: " + dettagli[i].quantity + ", Prezzo: " + dettagli[i].price + ";\n";
        }
        var total = $(document).find("#total-amount").text();
        total = parseFloat(total);
        saleDetailsString +="Totale: "+total+ "\n";

        // Mostra un alert con i dettagli della vendita e richiede la conferma dell'utente
        var confirmation = confirm(saleDetailsString);
        if(confirmation){
        // Invia una richiesta AJAX alla servlet per confermare la vendita
        $.ajax({
            method: 'POST',
            url: 'AggiungiVenditaServlet',
            contentType: "application/json",
            data: JSON.stringify(saleData)
        })
            .done(function() {
                alert('Vendita Salvata')
                window.location.href = "MostraProdottiCassiereServlet";
            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                var errorObj = JSON.parse(jqXHR.responseText);
                var errorMessage = errorObj.message;
                console.log("Messaggio di errore:", errorMessage);

                // Esempio: mostra un messaggio di errore
                alert("Errore: " + errorMessage);
            });
        }

}