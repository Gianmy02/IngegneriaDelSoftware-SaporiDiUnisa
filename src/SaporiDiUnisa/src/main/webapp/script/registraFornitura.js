"use strict";

$(document).ready(function(){
    const table_lottiInseriti = $("#lottiInseriti");

    $("#ajax-caller").click(function(){
        $.post("AggiungiLotto", {
                nome: $("#nome").val(),
                marchio: $("#marchio").val(),
                prezzo: $("#prezzo").val(),
                quantita: $("#quantita").val(),
                dataScadenza: $("#dataScadenza").val(),
                foto: $("#foto").val(),
            },
            function(lotto) {
                const tr = $("<tr></tr>")
                    .append($("<td></td>").text(lotto.nome))
                    .append($("<td></td>").text(lotto.marchio))
                    .append($("<td></td>").text(lotto.prezzo))
                    .append($("<td></td>").text(lotto.quantita))
                    .append($("<td></td>").text(lotto.dataScadenza));
                table_lottiInseriti.append(tr);
            }
        );
    });
});