/**
 * File javascript per la pagina di registrazione di una fornitura.
 * @author: Salvatore Ruocco
 */
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

    $("#registraFornitura-ajax").click(function(event){
        event.preventDefault();
        $.get({
            url: 'GetFornituraSession',
            success: function(json) {
                if(confirm(buildMessage(json)))
                    $('form')[1].submit();
            },
            error: function(error) {
                console.error(error);
            }
        });
    });

    function buildMessage(json){
        let message = "Riepilogo Fornitura:\n";
        for (let i = 0; i < json.length; i++)
            message += "Nome: " + json[i].nome + ", Marchio: " + json[i].marchio + ", Prezzo: " + json[i].prezzo + ", Quantità: " + json[i].quantita + ", Data scadenza: " + json[i].dataScadenza + "\n";
        message += "Sei sicuro di voler procedere?";
        return message;
    }

    $("#reset-button").click(function(){
        if(confirm("Sei sicuro di voler resettare la fornitura?")) {
            $.get({
                url: 'ResetFornituraSession',
                success: function () {
                    table_lottiInseriti.find('tbody').html('');
                },
                error: function (error) {
                    console.error(error);
                }
            });
        }
    });
});