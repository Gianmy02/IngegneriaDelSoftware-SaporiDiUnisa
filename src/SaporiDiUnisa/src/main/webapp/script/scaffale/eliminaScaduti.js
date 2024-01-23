"use strict";

$(document).ready(function() {


    $("#cancel-button").click(function(event) {
        event.preventDefault();

        var nomeProdotto;
        var azienda;
        var scadenza;
        var qntAttuale;
        var operazioni = "Riepilogo Rimozione:\n";


        $(".rigaScaduto").each(function() {

            nomeProdotto = $(this).find(".nomeProdotto").text();
            azienda = $(this).find(".azienda").text();
            scadenza = $(this).find(".scadenza").text();
            qntAttuale = $(this).find(".quantita").text();

            operazioni += "Prodotto: " + nomeProdotto + ", Azienda: " + azienda + ", Scadenza: " + scadenza + ", Qnt Attuale Scaffale: " + qntAttuale + ";\n";

        });

        if(operazioni.length > 21){
            if(confirm(operazioni))
                $("#formRimozione").submit();
            else
                alert("L'operazione è stata annullata");
        }
        else
            alert("Non esistono prodotti scaduti sullo scaffale");
    });
});