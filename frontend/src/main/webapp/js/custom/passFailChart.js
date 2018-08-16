var returnedResultPass;
var returnedResultFails;
var passFailChart;

(function() {
	getCountPass();
	getCountFails();
})();

$(document).ready(function(){
	renderPassFailChart();
});

function renderPassFailChart(){
	passFailChart = new Chart('chart_2', {
		type : 'doughnut',
		options : option,
		data : data
	});
}

function getCountPass() {
	$.get({
		url : "http://localhost:8080/ProjectA/results-table/getCountPass",
		async : false,
		cache : false,
		type : "GET"
	}, function(resultPass) {
		returnedResultPass = parseFloat(resultPass);
	});
}

function getCountFails() {
	$.get({
		url : "http://localhost:8080/ProjectA/results-table/getCountFails",
		async : false,
		cache : false,
		type : "GET"
	}, function(resultFails) {
		returnedResultFails = parseFloat(resultFails);
	});
}
var data = {
	labels : [ "Pass", "Fail" ],
	datasets : [ {
		label : "Pass/Fail",
		data : [ returnedResultPass, returnedResultFails ],
		backgroundColor : ["#c1d4f7","rgba(255,99,132,0.4)"],
		borderColor : ["cornflowerblue","#ff6384"],
		borderWidth : 2,
		hoverBackgroundColor : ["#c1d4f7","rgba(255,99,132,0.4)"],
		hoverBorderColor : ["#c1d4f7","rgba(255,99,132,0.4)"],
	}]

};
var option = {
	responsive : false,

	scales : {
		display: false,

	}

};
