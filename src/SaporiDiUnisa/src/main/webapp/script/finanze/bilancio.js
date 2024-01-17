"use strict";

const drawPieChart = (spese, incasso) =>
{
    new Chart('pie-chart',
    {
        type: 'pie',
        data:
        {
            labels: ['Spese', 'Incasso'],
            datasets:
            [{
                data: [spese, incasso],
                backgroundColor: ['rgb(255, 0, 0)', 'rgb(0, 255, 0)']
            }]
        },
        options:
        {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}