function confirmSale() {
    var saleData = [];

    $(".vendita-item").each(function(index, element) {
        var productId = $(element).attr("id");
        var quantity = $(element).find(".item-quantity").val();
        var price = $(element).find(".item-price").text();


        price = parseFloat(price);

        if (quantity > 0) {
            saleData.push({
                productId: parseInt(productId), // Assicura che productId sia un numero intero
                quantity: parseInt(quantity), // Assicura che quantity sia un numero intero
                price: price
            });
        }
    });

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
        });

}
