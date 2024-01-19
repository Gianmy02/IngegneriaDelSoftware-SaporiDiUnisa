"use strict";

$(document).ready(function(){
    const table_lottiInseriti = $("#lottiInseriti");

    $("#registraFornitura-ajax").click(function(event){
        event.preventDefault();
        $.get({
            url: 'GetFornituraSession',
            success: function(json) {
                if(confirm(_buildMessage(json)))
                    $('form')[1].submit();
            },
            error: function(error) {
                console.error(error);
            }
        });
    });

    function _buildMessage(json){
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