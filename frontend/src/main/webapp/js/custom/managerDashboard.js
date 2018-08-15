$(document).ready(function(){
	loader();
	retrieveMostRecentResults();
	getAllUsers()
});

function loader(){
	$("#spinner").css("display","inline");
setTimeout(function(){
	$("#spinner").animate({opacity:0,},300);
},2500);
setTimeout(function(){
	$("#spinner").css("display","none");
	$(".container").css("display","block");
	$(".footer").css("display","block");
	document.getElementById("container_box2").scrollIntoView();
},2800);
setTimeout(function(){
	$(".container").css("visibility","visible").animate({opacity:1,},300);
	$(".footer").css("visibility","visible").animate({opacity:1,},300);
},3000);
}


function retrieveMostRecentResults(){
	$.get({
		url: "http://localhost:8080/ProjectA/results-table/retrieveMostRecentResults",
		cache: false,		
		type : "GET",
	}, function (response){
		addResultsToTable(response);		
	});
}

function addResultsToTable(response){
	for (var i = 0; i < response.length;i++){
		$("   <tr><td>" + retrieveUserName(response[i].userName) + "</td><td>" +
				response[i].userName + "</td><td>" + "Introduction To Python" + "</td><td>" +
				response[i].result.charAt(0).toUpperCase() + response[i].result.slice(1) +
				"</td><td>" + configurePercentage(response[i].numberOfQuestionsCorrect) +
				"</td><td>" + configureTimeTaken(response[i].timeTakenPerQuestion)
				+ "</td></tr>").appendTo("#latestResults");		
	}
}

function retrieveUserName(userName){
	var fullName = "";
	$.get({
		url: "http://localhost:8080/ProjectA/api/getName/" + userName,
		async: false,
		cache: false,		
		type : "GET",
	}, function (response){
		fullName = response;
	});
	return fullName;
}

function configurePercentage(numberOfQuestionsCorrect){
	return ((100 * numberOfQuestionsCorrect) / 6).toFixed(2) + "%";
}

function configureTimeTaken(timeTaken){
	var totalTimeTaken = 0;
	var timeTakenList = JSON.parse("[" + timeTaken + "]");
	for (var i = 0; i < timeTakenList.length;i++){
		totalTimeTaken = totalTimeTaken + timeTakenList[i];
	}
	return totalTimeTaken + " seconds";
}

function getAllUsers(){
	$.get({
		url: "http://localhost:8080/ProjectA/api/getAllUsers",
		cache: false,		
		type : "GET",
	}, function (response){
		fillDropDown(response);
	});
	
}

function fillDropDown(response){
	var select = document.getElementById("DropDown");
	$("select#DropDown").prop('selectedIndex',0);
	for (var i = 0; i < response.length;i++){
		select.options[select.options.length] = new Option(retrieveUserName(response[i].userName), response[i].userName);
	}
}