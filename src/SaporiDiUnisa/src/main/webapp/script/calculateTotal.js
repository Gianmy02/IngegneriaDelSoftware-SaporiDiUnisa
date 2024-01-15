"use strict";
$(document).ready(function() {
    calculateTotal();
});
function calculateTotal() {
    var items = document.getElementsByClassName("vendita-item");
    var totale = 0;
    console.log(items.length);

    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var quantita = parseInt(item.querySelector(".item-quantity").value);

        var prezzo = parseFloat(item.querySelector(".item-price").innerText);

        var subTotale = quantita * prezzo;
        totale += subTotale;
    }
    $("#total-amount").text(totale.toFixed(2));
}


