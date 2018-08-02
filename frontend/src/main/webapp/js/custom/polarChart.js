 var ctx = document.getElementById('myChart').getContext('2d');
        var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'polarArea',

    // The data for our dataset
   data: {
        labels: ["Variables", "Operators", "Functions"],

        datasets: [{
            backgroundColor: ['rgb(41, 123, 164)', 'rgb(51, 123, 163)', 'rgb(71, 163, 163)'],
            borderColor: 'rgb(71, 123, 163)',
            data: [5 , 1, 1],
        }]
    },
        
    // Configuration options go here
    options: {}
});