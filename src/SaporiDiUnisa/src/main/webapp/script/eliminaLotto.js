

$.ajax({
    method: 'POST',
    url: 'EliminaLotto',
    data: JSON.stringify(saleData)
})
    .done(function() {
        alert('Vendita Salvata')
        window.location.href = "MostraProdottiCassiereServlet";
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
    });
}