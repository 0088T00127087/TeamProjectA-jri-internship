var chartA;

$(document).ready(function(){
	renderChartA();
});

var dataA = {
		  labels: ["Intro to Python", "Variables", "Functions", "Operators", "Objects", "Classes"],
		  datasets: [{
		    label: "Tric by Video",
		    backgroundColor: "rgba(255,99,132,0.2)",
		    borderColor: "rgba(255,99,132,1)",
		    borderWidth: 2,
		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
		    hoverBorderColor: "rgba(255,99,132,1)",
		    data: [3, 25, 30, 81, 56, 70 ],
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
		        color: "green"
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
		chartA = new Chart('chart_0', {
			  type: 'horizontalBar',
			  options: optionA,
			  data: dataA
			});
		}
	
	
	// Get average time  
	function getAllTimes() {
		$.get({
			url : "http://localhost:8080/ProjectA/results-table/getAllTime",
			async : false,
			cache : false,
			type : "GET"
		}, function(response) {
			
			console.log(response);
			
		});
	}
