$(document).ready(function(){	
	retrieveMostRecentResults();
});


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
		console.log(typeof timeTakenList[i]);
		totalTimeTaken = totalTimeTaken + timeTakenList[i];
	}
	return totalTimeTaken + " seconds";
}