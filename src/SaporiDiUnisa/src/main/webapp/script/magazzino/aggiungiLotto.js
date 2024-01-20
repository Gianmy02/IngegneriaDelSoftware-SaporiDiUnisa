"use strict";

$(document).ready(function(){
    const table_lottiInseriti = $("#lottiInseriti");

    const paragraph_errors = $("#errors");
    $("#ajax-caller").click(function(){
        const formData = new FormData($('form')[0]);
        $.post({
            url: 'AggiungiLotto',
            data: formData,
            contentType: false,
            processData: false,
            success: function(json) {
                if(json.errors != null){
                    paragraph_errors.attr("display", "block");
                    paragraph_errors.text(json.errors);                }
                else {
                    paragraph_errors.attr("display", "none");
                    paragraph_errors.text("");

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
            },
            error: function(error) {
                console.error(error);
            }
        });
    });

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