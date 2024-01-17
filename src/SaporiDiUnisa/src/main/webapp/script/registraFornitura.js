"use strict";

$(document).ready(function(){
    const table_lottiInseriti = $("#lottiInseriti");
    $("#ajax-caller").click(function(){
        const formData = new FormData($('form')[0]);
        $.ajax({
            url: 'AggiungiLotto',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(lotto) {
                const tr = $("<tr></tr>")
                    .append($("<td></td>").text(lotto.nome))
                    .append($("<td></td>").text(lotto.marchio))
                    .append($("<td></td>").text(lotto.prezzo))
                    .append($("<td></td>").text(lotto.quantita))
                    .append($("<td></td>").text(lotto.dataScadenza))
                table_lottiInseriti.append(tr);
            },
            error: function(error) {
                console.error(error);
            }
        });
    });
});