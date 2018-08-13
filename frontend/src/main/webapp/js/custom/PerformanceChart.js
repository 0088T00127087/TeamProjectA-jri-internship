var returnedResult;


(function () {
	getUserResult();
})();

function getUserResult(){
			$.get({
				url: "http://localhost:8080/ProjectA/results-table/getUserResult/jEnright",
				async: false,
				cache: false,		
				type : "GET"
			},function(result){
				returnedResult = parseFloat(result);
			});
		}


var data = {
		  labels: ["Python","Java","SQL","Automation","Unit Testing A"],
		  datasets: [{
		    label: "Dataset #1",
		    data: [returnedResult,5, 5, 5, 5],
		    backgroundColor: "rgba(255,99,132,0.2)",
		    borderColor: "rgba(255,99,132,1)",
		    borderWidth: 2,
		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
		    hoverBorderColor: "rgba(255,99,132,1)",
		    
		  }]
		};

		var option = {
		  responsive: false,
		  scales: {
		    yAxes: [{
		      stacked: true,
		      gridLines: {
		        display: true,
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
	
		
		Chart.Bar('chart_1', {
		  options: option,
		  data: data
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//
		