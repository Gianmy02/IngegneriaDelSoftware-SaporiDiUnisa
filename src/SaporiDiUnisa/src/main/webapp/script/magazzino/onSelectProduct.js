"use strict";

$(document).ready(function(){
    const productsList_html = $("#productsList");
    const productsList = [];
    // Popolo la select dei prodotti
    $.get({
        url: 'GetProdotti',
        success: function(json) {
            for(let i= 0; i < json.length; i++){
                const s = json[i].nome + " - " + json[i].marchio;
                productsList.push(s);
                productsList_html.append($("<option></option>").text(s));
            }
        },
        error: function(error) {
            console.error(error);
        }
    });

    const input_nome = $("#nome");
    const input_marchio = $("#marchio");
    const input_foto = $("#foto");
    const label_foto = $("#label-foto");
    // Ascoltatore per il campo nome
    input_nome.change(function(){
        const val_nome = input_nome.val();
        if(productsList.includes(val_nome)){
            // Settare i campi nome e marchio in modo giusto
            const split = val_nome.split(" - ");
            input_nome.val(split[0]);
            input_marchio.val(split[1]);
            _hideFoto()
        }
        else
            _showFoto();
    });

    // Ascoltatore per il campo marchio
    input_marchio.change(function(){
        if(_marchioIsInList($(this)))
            _hideFoto();
        else
            _showFoto();
    });

    // Controllo che il marchio sia presente nella lista dei prodotti
    function _marchioIsInList(input){
        for(let i = 0; i < productsList.length; i++){
            const split = productsList[i].split(" - ");
            if(split[1] === input.val())
                return true;
        }
        return false;
    }

    function _hideFoto(){
        input_foto.hide();
        input_foto.attr("required", false);
        label_foto.hide();
    }

    function _showFoto(){
        input_foto.show();
        input_foto.attr("required", true);
        label_foto.show();
    }
});