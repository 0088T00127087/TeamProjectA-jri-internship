var chartA;

$(document).ready(function(){
	renderChartA();
});

var dataA = {
		  labels: ["Python","Java","SQL","Automation","Unit Testing A"],
		  datasets: [{
		    label: "Dataset #2",
		    backgroundColor: "rgba(255,99,132,0.2)",
		    borderColor: "rgba(255,99,132,1)",
		    borderWidth: 2,
		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
		    hoverBorderColor: "rgba(255,99,132,1)",
		    data: [65, 59, 30, 81, 56 ],
		  }]
		};

		var optionA = {
				legend: {
		            display: false
		         },
		  responsive: false,
		  scales: {
		    yAxis: [{
		      stacked: true,
		      gridLines: {
		        display: false,
		        color: "red"
		      }
		    }],
		    xAxis: [{
		      gridLines: {
		        display: false
		      }
		    }]
		  }
		};
		
	function renderChartA(){
		chartA = Chart.Radar('chart_0', {
			  options: optionA,
			  data: dataA
			});
	}
