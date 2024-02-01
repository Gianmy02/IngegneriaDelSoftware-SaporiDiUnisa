"use strict";

$(document).ready(function() {


    $("#cancel-button").click(function(event) {
        event.preventDefault();

        var idLotto;
        var nomeProdotto;
        var scadenza;
        var qntAttuale;
        var operazioni = "Riepilogo Rimozione:\n\n";


        $(".rigaScaduto").each(function() {

            idLotto = $(this).find(".idLotto").text();
            nomeProdotto = $(this).find(".nomeProdotto").text();
            scadenza = $(this).find(".scadenza").text();
            qntAttuale = $(this).find(".quantita").text();

            operazioni += "Lotto: " + idLotto + ", Prodotto: " + nomeProdotto + ", Scadenza: " + scadenza + ", Qnt Attuale Scaffale: " + qntAttuale + ";\n\n";

        });

        if(operazioni.length > 22){
            if(confirm(operazioni))
                $("#formRimozione").submit();
            else
                alert("L'operazione è stata annullata");
        }
        else
            alert("Non esistono prodotti scaduti sullo scaffale");
    });
});