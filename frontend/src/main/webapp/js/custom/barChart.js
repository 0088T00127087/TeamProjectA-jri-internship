google.charts.load('current', {'packages':['bar']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Name', 'Intro', 'Variables', 'Functions'],
          ['John', 100, 40, 20],
          ['Shannen', 70, 46, 50],
          ['Ian', 60, 20, 30],
          ['Joe', 30, 54, 50]
        ]);

        var options = {
          chart: {
            title: 'Employee Performance',
            subtitle: 'Results of topics ',
          },
          bars: 'horizontal' // Required for Material Bar Charts.
        };

        var chart = new google.charts.Bar(document.getElementById('barchart_material'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
      }
