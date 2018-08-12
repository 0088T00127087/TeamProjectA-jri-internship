function getUserResult(){
			$.get({
				url: "http://localhost:8080/ProjectA/results-table/getUserResult/jEnright",
				async: true,
				cache: false,		
				type : "GET"
			},function(result){
				console.log(typeof parseFloat(result));
				console.log (typeof 4);
				return parseFloat(result);
			});
		}


var data = {
		  labels: ["Python","Java","SQL","Automation","Unit Testing A"],
		  datasets: [{
		    label: "Dataset #1",
		    backgroundColor: "rgba(255,99,132,0.2)",
		    borderColor: "rgba(255,99,132,1)",
		    borderWidth: 2,
		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
		    hoverBorderColor: "rgba(255,99,132,1)",
		    data: [getUserResult(), 5, 5, 5, 5, ],
		    
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
		
		
		