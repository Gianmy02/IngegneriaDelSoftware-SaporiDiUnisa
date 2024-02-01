"use strict";

$(document).ready(function() {


    $("#confirm-button").click(function(event) {
        event.preventDefault();

        var qnt;
        var idLotto;
        var nomeProdotto;
        var scadenza;
        var operazioniS = "Riepilogo Operazioni Scaffale:\n\n";
        var operazioniM = "Riepilogo Operazioni Magazzino:\n\n";
        var operazioniTotali = "";
        var qntMax;
        var error = false;

        $(".RigaS").each(function() {

            qnt = $(this).find("input[type='number']").val();
            qntMax = parseInt($(this).find("input[type='number']").attr("max"), 10);

            if(qnt === "" || isNaN(qnt))
            {
                error = true;
                console.log("error")
            }
            else
            {

                if (qnt > qntMax)
                {
                    alert("La quantità specificata è maggiore di quella disponibile");
                    error = true;
                    return;
                }

                if (qnt < 0)
                {
                    alert("La quantità non può essere minore di 0");
                    error = true;
                    return;
                }

                if (qnt > 0)
                {
                    idLotto = $(this).find(".idLottoS").text();
                    nomeProdotto = $(this).find(".nomeProdottoS").text();
                    scadenza = $(this).find(".dataScadenzaS").text();

                    operazioniS += "Lotto: " + idLotto + ", Prodotto: " + nomeProdotto + ", Scadenza: " + scadenza + ", Qnt Aggiunta: " + qnt + ";\n\n";
                }
            }

        });

        $(".RigaM").each(function() {

            qnt = $(this).find("input[type='number']").val();
            qntMax = parseInt($(this).find("input[type='number']").attr("max"), 10);

            if(qnt === "" || isNaN(qnt))
            {
                error = true;
            }
            else
            {
                if (qnt > qntMax)
                {
                    alert("La quantità specificata è maggiore di quella disponibile");
                    error = true;
                    return;
                }

                if (qnt < 0)
                {
                    alert("La quantità non può essere minore di 0");
                    error = true;
                    return;
                }

                if (qnt > 0)
                {
                    idLotto = $(this).find(".idLottoM").text();
                    nomeProdotto = $(this).find(".nomeProdottoM").text();
                    scadenza = $(this).find(".dataScadenzaM").text();

                    operazioniM += "Lotto: " + idLotto + ", Prodotto: " + nomeProdotto + ", Scadenza: " + scadenza + ", Qnt Aggiunta: " + qnt + ";\n\n";
                }
            }

        });

        if(operazioniS.length > 32)
            operazioniTotali += operazioniS;
        if(operazioniM.length > 33)
            operazioniTotali += operazioniM;

        if(operazioniTotali.length > 0 && !error)
        {
            if(confirm(operazioniTotali))
                $("#formAggiunta").submit();
            else
                alert("L'operazione è stata annullata");
        }
        else if(error)
            alert("sono stati forniti degli input non validi");
        else
            alert("non sono state effettuate operazioni");
    });
});

