"use strict";

$(document).ready(function() {


    $("#confirm-button").click(function(event) {
        event.preventDefault();

        var qnt;
        var nomeProdotto;
        var azienda;
        var scadenza;
        var qntAttuale;
        var operazioniS = "Riepilogo Operazioni Scaffale:\n";
        var operazioniM = "Riepilogo Operazioni Magazzino:\n";
        var operazioniTotali = "";
        var qntMax;

        $(".RigaS").each(function() {

            qnt = $(this).find("input[type='number']").val();
            qntMax = parseInt($(this).find("input[type='number']").attr("max"), 10);

            if(qnt > qntMax){
                alert("Si è verificato un errore durante la richiesta per l'inserimento di un prodotto");
                return;
            }

            if (qnt > 0) {
                nomeProdotto = $(this).find(".nomeProdottoS").text();
                azienda = $(this).find(".nomeAziendaS").text();
                scadenza = $(this).find(".dataScadenzaS").text();
                qntAttuale = $(this).find(".qntAttualeS").text();

                operazioniS += "Prodotto: " + nomeProdotto + ", Azienda: " + azienda + ", Scadenza: " + scadenza + ", Qnt Attuale Scaffale: " + qntAttuale + ", Qnt Aggiunta: " + qnt + ";\n";
            }

        });

        $(".RigaM").each(function() {

            qnt = $(this).find("input[type='number']").val();
            qntMax = parseInt($(this).find("input[type='number']").attr("max"), 10);

            if(qnt > qntMax){
                alert("Si è verificato un errore durante la richiesta per l'inserimento di un prodotto");
                return;
            }

            if (qnt > 0) {
                nomeProdotto = $(this).find(".nomeProdottoM").text();
                azienda = $(this).find(".nomeAziendaM").text();
                scadenza = $(this).find(".dataScadenzaM").text();
                qntAttuale = $(this).find(".qntAttualeM").text();

                operazioniM += "Prodotto: " + nomeProdotto + ", Azienda: " + azienda + ", Scadenza: " + scadenza + ", Qnt Attuale Magazzino: " + qntAttuale + ", Qnt Aggiunta: " + qnt + ";\n";
            }

        });

        if(operazioniS.length > 31)
            operazioniTotali += operazioniS;
        if(operazioniM.length > 32)
            operazioniTotali += operazioniM;

        if(operazioniTotali.length >0){
            if(confirm(operazioniTotali))
                $("#formAggiunta").submit();
            else
                alert("L'operazione è stata annullata");
        }
        else
            alert("Non sono state effettuate operazioni");

    });
});

