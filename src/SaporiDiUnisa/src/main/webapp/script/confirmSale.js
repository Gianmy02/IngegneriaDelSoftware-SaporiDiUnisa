function confirmSale() {
    var saleData = [];

    $(".vendita-item").each(function(index, element) {
        var productId = $(element).data("product-id");
        var quantity = $(element).find(".item-quantity").val();
        var price = $(element).find(".item-price").text();

        price = parseFloat(price.replace(/\s|€/g, ''));

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
        method: "POST",
        url: "${pageContext.request.contextPath}/AggiungiVenditaServlet",
        contentType: "application/json",
        data: JSON.stringify(saleData)
    })
        .done(function() {
            window.location.href = "${pageContext.request.contextPath}/index.jsp";
        })
        .fail(function() {
            console.error("Errore durante la conferma della vendita: " + textStatus);
        });

}
