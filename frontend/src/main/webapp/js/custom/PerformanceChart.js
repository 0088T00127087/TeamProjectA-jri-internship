var returnedResult;


(function () {
	getOverallResult();
})();

//function getUserResult(userAccount){
//			$.get({
//				url: "http://localhost:8080/ProjectA/results-table/getUserResult/" + userAccount,
//				async: false,
//				cache: false,		
//				type : "GET"
//			},function(result){
//				returnedResult = parseFloat(result);
//			});
//		}


function getOverallResult(){
	$.get({
		url: "http://localhost:8080/ProjectA/results-table/getOverallResult",
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
	
		
		var myChart = new Chart('chart_1', {
			type: 'bar',
			options: option,
			data: data
		});
		
		function getUserResult(userAccount){
			$.get({
				url: "http://localhost:8080/ProjectA/results-table/getUserResult/" + userAccount,
				async: false,
				cache: false,		
				type : "GET"
			},function(result){
				myChart.data.datasets[0].data[0] = parseFloat(result);
				myChart.update();
				
			});
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//
		