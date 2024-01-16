"use strict";

new Chart('pie-chart',
{
    type: 'pie',
    data:
    {
        labels: ['Spese', 'Incasso'],
        datasets:
        [
            {
                data: ['${bilancio.spese}', '${bilancio.incasso}'],
                backgroundColor: ['rgb(255, 0, 0)', 'rgb(0, 255, 0)']
            }
        ]
    },
    options:
    {
        responsive: true,
        maintainAspectRatio: false
    }
});