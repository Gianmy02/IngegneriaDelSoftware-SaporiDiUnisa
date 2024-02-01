"use strict";

$(document).ready(function(){
    const table_lottiInseriti = $("#lottiInseriti");

    const list_errors = $("#errors");
    $("#ajax-caller").click(function(){
        const formData = new FormData($('form')[0]);
        $.post({
            url: 'AggiungiLotto',
            data: formData,
            contentType: false,
            processData: false,
            success: function(json) {
                list_errors.css("display", "none");
                list_errors.empty();

                _buildTableRow(json);
            },
            error: function(error) {
                const errors = error.responseJSON.message;
                list_errors.empty();
                const array_errors = errors.split("\n");
                for(let i = 0; i < array_errors.length - 1; i++)
                    list_errors.append($("<li></li>")
                        .text(array_errors[i])
                        .css("color", "red")
                        .css("text-align", "left"));
                list_errors.css("display", "block");
            }
        });
    });

    // Ottiene la lista dei lotti in sessione quando si carica la pagina
    $.get({
        url: 'GetFornituraSession',
        success: function(json) {
            for(let i = 0; i < json.length; i++)
                _buildTableRow(json[i]);
        },
        error: function(error) {
            console.error(error);
        }
    });

    // Costruisce una riga della tabella
    function _buildTableRow(json){
        const tr = $("<tr></tr>")
            .append($("<td></td>").text(json.nome))
            .append($("<td></td>").text(json.marchio))
            .append($("<td></td>").text(json.prezzo))
            .append($("<td></td>").text(json.quantita))
            .append($("<td></td>").text(json.dataScadenza))
            .append($("<td></td>")
                .append($("<button></button>")
                    .attr("id", "btn-" + json.keyId)
                    .text("Elimina")
                    .click(function () {
                        _deleteLotto(json.keyId)
                    })
                )
            );
        table_lottiInseriti.append(tr);
    }

    // Elimina il lotto dalla tabella
    function _deleteLotto(keyId){
        $.get('RimuoviLotto', { keyId: keyId },
            function() {
                const tr = $("#btn-" + keyId).parent().parent();
                tr.remove();
            }
        );
    }
});