var returnedResult;
var chartUserAverage;
var userName;

$(document).ready(function(){
	//renderChartB();
	renderChartSupp();
});


(function () {
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	console.log(userName);
	getUserResult(userName);
})();

//var dataB = {
//		  labels: ["Python","Java","SQL","Automation","Unit Testing A"],
//		  datasets: [{
//		    label: "%",
//		    data: [returnedResult,(Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2)],
//		  //  data: [returnedResult,0,0,0,0],
//		    backgroundColor: "#c1d4f7",
//		    borderColor: "cornflowerblue",
//		    borderWidth: 2,
//		    hoverBackgroundColor: "rgba(255,99,132,0.4)",
//		    hoverBorderColor: "rgba(255,99,132,1)",
//		    
//		  }]
//		};

//		var optionB = {
//				legend: {
//		            display: false
//		         },
//		  responsive: false,
//		  scales: {
//		    yAxes: [{
//		    	display:false,
//		      stacked: true,
//		      gridLines: {
//		        display: false,
//		      }
//		    }],
//		    xAxes: [{
//		    	display:false,
//		      gridLines: {
//		        display: false
//		      }
//		    }]
//		  }
//		};
		
		var dataUser = {
				  labels: ["Python","Java","SQL","Automation","Unit Testing A"],
				  datasets: [{
				    label: "%",
				    data: [returnedResult,(Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2), (Math.random() *100).toFixed(2)],
				    backgroundColor: "#c1d4f7",
				    borderColor: "cornflowerblue",
				    borderWidth: 2,
				    hoverBackgroundColor: "rgba(255,99,132,0.4)",
				    hoverBorderColor: "rgba(255,99,132,1)",
				    
				  }]
				};

				var optionUser = {
							legend: {
						            display: false
						         },

				  responsive: false,
				  scales: {
				    yAxes: [{
				    	display:false,
				      stacked: true,
				      gridLines: {
				        display: false,
				      }
				    }],
				    xAxes: [{
				    	display:false,
				      gridLines: {
				        display: false
				      }
				    }]
				  }
				};
	
		
//function renderChartB(){
//	chartOverallAverage = Chart.Radar('chart_overall_average', {
//		  options: optionB,
//		  data: dataB,
//		});
//}

function renderChartSupp(){
	chartUserAverage = Chart.Radar('chart_overall_user', {
		  options: optionUser,
		  data: dataUser,
		});
}


//function getOverallResult(){
//	$.get({
//		url: "http://localhost:8080/ProjectA/results-table/getOverallResult",
//		async: false,
//		cache: false,		
//		type : "GET"
//	},function(result){
//		returnedResult = ((result / 6) * 100).toFixed(2);	
//	});
//}
		
function getUserResult(userName){
	var userAccount = userName;
			
	if(userAccount === "-1"){
	} else {
		$.get({
			url: "http://localhost:8080/ProjectA/results-table/getUserResult/" + userAccount,
			async: false,
			cache: false,		
			type : "GET"
		},function(result){
			returnedResult = ((result / 6) * 100).toFixed(2);
			});	
			
	}	
	}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//
		