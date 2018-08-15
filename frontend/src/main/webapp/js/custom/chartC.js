var chartC;

$(document).ready(function(){
	renderChartC();
});

var dataC = {
		  labels: ["Python","Java","SQL","Automation","Unit Test B"],
		  datasets: [{
		    label: "Dataset #3",
		    backgroundColor: "rgba(255,99,132,0.2)",
		    borderColor: "rgba(255,99,132,1)",
		    borderWidth: 2,
		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
		    hoverBorderColor: "rgba(255,99,132,1)",
		    data: [65, 59, 30, 81, 56, ],
		  }]
		};

		var optionC = {
				legend: {
		            display: false
		         },
		  responsive: false,
		  scales: {
		    yAxes: [{
		      stacked: true,
		      gridLines: {
		        display: false,
		        color: "rgba(255,99,132,0.2)"
		      }
		    }],
		    xAxes: [{
		      gridLines: {
		        display: false
		      }
		    }]
		  }
		};
		
function renderChartC(){
	chartC = Chart.Line('chart_2', {
			options: optionC,
			data: dataC
		});
}
