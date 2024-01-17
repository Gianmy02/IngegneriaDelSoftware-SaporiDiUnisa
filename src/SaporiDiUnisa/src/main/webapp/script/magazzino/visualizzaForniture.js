/**
 * File javascript per la pagina di visualizzazione della lista di forniture.
 * @author: Salvatore Ruocco
 */
"use strict";

$(document).ready(function(){
    const fornitureTable = $("#forniture-table");
    $.get({
        url: 'GetListForniture',
        success: function(json) {
            for (let i = 0; i < json.length; i++){
                const tr = $("<tr></tr>")
                    .append($("<td></td>").text(json[i]._id))
                    .append($("<td></td>").text(json[i].giorno))
                    .append($("<td></td>").text(json[i].lotti));
                fornitureTable.append(tr);
            }
        },
        error: function(error) {
            console.error(error);
        }
    });

});