"use strict";

$(document).ready(function(){
    const productsList_html = $("#productsList");
    const marchioList_html = $("#marchioList");
    const productsList = [];
    const marchioList = [];
    // Popolo la select dei prodotti
    $.get({
        url: 'GetProdotti',
        success: function(json) {
            for(let i= 0; i < json.length; i++){
                const nome = json[i].nome;
                const marchio = json[i].marchio;

                productsList.push(nome);
                marchioList.push(marchio);
                productsList_html.append($("<option></option>").text(nome));
                marchioList_html.append($("<option></option>").text(marchio));
            }
        },
        error: function(error) {
            console.error(error);
        }
    });

    const input_nome = $("#nome");
    const input_marchio = $("#marchio");

    // Ascoltatore per il campo nome
    input_nome.change(_inputListener);

    // Ascoltatore per il campo marchio
    input_marchio.change(_inputListener);

    function _inputListener(){
        if(_productIsInList())
            _hideFoto();
        else
            _showFoto();
    }

    // Controllo che il marchio sia presente nella lista dei prodotti
    function _productIsInList(){
        for(let i = 0; i < productsList.length; i++)
            if(productsList[i] === input_nome.val() && marchioList[i] === input_marchio.val())
                return true;

        return false;
    }

    const input_foto = $("#foto");
    const label_foto = $("#label-foto");
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