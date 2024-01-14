"use strict";
$.post("AggiungiLotto", {
        nome: $("#nome").val(),
        marchio: $("#marchio").val(),
        prezzo: $("#prezzo").val(),
        quantita: $("#quantita").val(),
        dataScadenza: $("#dataScadenza").val(),
        foto: $("#foto").val(),
    },
    function(data) {
        // aggiungere il lotto alla tabella
    }
);