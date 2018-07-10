google.charts.load('current', {'packages':['bar']});
      google.charts.setOnLoadCallback(drawStuff);

      function drawStuff() {
        var data = new google.visualization.arrayToDataTable([
          ['Name', 'Percentage'],
          ['John', 44],
          ['Shannen', 31],
          ['Ian', 12],
          ['Jack', 10],
          ['Denis', 3]
        ]);

        var options = {
          title: 'Team Scores',
          width: 900,
          legend: { position: 'none' },
          chart: { title: 'Python Training Test Scores',
                   subtitle: 'pass by percentage' },
          bars: 'horizontal', // Required for Material Bar Charts.
          axes: {
            x: {
              0: { side: 'top', label: 'Percentage'} // Top x-axis.
            }
          },
          bar: { groupWidth: "90%" },
        };

        var chart = new google.charts.Bar(document.getElementById('top_x_div'));
        chart.draw(data, options);
      };
