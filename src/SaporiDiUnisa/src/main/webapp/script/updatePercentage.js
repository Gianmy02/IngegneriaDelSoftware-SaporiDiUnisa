function udpatePercentage() {
    var prezzoBase = parseFloat($("#price").text().replace('Prezzo Base: ', '').replace('€', ''));    var percentualeSconto = parseFloat($("#discount").val());
    var discountAmount = (prezzoBase * percentualeSconto) / 100;
    var discountedPrice = prezzoBase - discountAmount;
    if(isNaN(discountedPrice))
        discountedPrice = 0.00;
    // Calcolare il prezzo scontato totale
    $("#discountedPrice").text(discountedPrice.toFixed(2));
}