var userName;
var questionAndAnswerBank;
var questionNumber = 0;
var correctAnswerIdList = [];
var wrongAnswerIdList = [];
var timeTakenToAnswerList = [];

$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/introductionToPython.html");
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/retrieveUserTopicLegibility/" + userName + "/1/",
		cache: false,
		type : "GET",
	}, function(response){
		if (response === "true"){
			$.get({
				url: "http://localhost:8080/ProjectA/course-registration/checkTheUsersTopicStatus/" + userName + "/1/",
				cache: false,
				type : "GET",
			}, function(response){
				if (response === 0){
					customisation();
					updateDatabaseAndNotifyManager();
					retrieveQuestions();
					videoFunctionality();
					$("#pageContent").css("display","inline");
					$("#homepageVideo").get(0).prop("volume", this.value);		
				} else if (response === 1){
					$("#navigationError").css("display","inline");
					registerFailureAndRevertToNotStarted();
					setTimeout(function(){
						document.location.href = '/frontend/pages/topics.html?username=' + userName;
					},5000);
				} else if (response === 2){
					$("#managerApprovalRequired").css("display","inline");
					setTimeout(function(){
						document.location.href = '/frontend/pages/topics.html?username=' + userName;
					},5000);
				}
			});
		} else {
			$("#managerApprovalRequired").css("display","inline");
			setTimeout(function(){
				document.location.href = '/frontend/pages/topics.html?username=' + userName;
			},5000);
		}
	});	
	$("input[name='answers']").change(function(){
	    $("#submitAnswer").css("background-color","green").prop("disabled",false);
	});
});

function registerFailureAndRevertToNotStarted(){
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/registerFirstFailure/" + userName + "/1/",
		cache: false,
		type : "GET",
	}, function(response){
		console.log(response);
	});
}

$(function() {
    $(document).scrollTop( $("#homepageVideo").offset().top );  
    $('#volume').on('change', function() {
    	$("#homepageVideo").get(0).prop("volume", this.value);
    });	
});

function updateDatabaseAndNotifyManager(){
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/updateToInProgress/" + userName + "/1/",
		cache: false,		
		type : "GET",
	}, function(response){
		console.log(response);
	});	
}

function managerReviewCheck(){
}

function retrieveQuestions(){
	$.get({
		url: "/frontend/QuestionBankServlet",
		cache: false,		
		type : "GET",
		data : {username: userName}
	}, function(response){
		questionAndAnswerBank = JSON.parse(response);
	});	
}

function customisation(){
	$.get({
		url: "/frontend/HomepageInitialLoadServlet",
		cache: false,		
		type : "GET",
		data : {username: userName}
	}, function(response){
		$("#name").text(response);
	});	
}

function videoFunctionality(){
	$("#homepageVideo").get(0).play();
	$('#homepageVideo').on('ended',function(){
		questionModalLoader();
		loadNextQuestion();
		makeTranscriptBlank();
	});	
}

function submitAnswer(){
	if (questionNumber <= 5){
		chosenAnswer = $('input[name=answers]:checked', '#answerList').val();
		if (chosenAnswer === questionAndAnswerBank[questionNumber].correctAnswer){
			correctAnswerIdList.push(questionAndAnswerBank[questionNumber].questId);
		} else {
			wrongAnswerIdList.push(questionAndAnswerBank[questionNumber].questId);
		} 
		$("#answerA").prop("checked",false);
		$("#answerB").prop("checked",false);
		$("#answerC").prop("checked",false);
		$("#answerD").prop("checked",false);
		$("#submitAnswer").css("background-color","lightgray").prop("disabled",true);
		questionNumber++;
		loadNextQuestion();
	}
}

function loadNextQuestion(){
	if (questionNumber <= 5){
		var questionAnswerList = [questionAndAnswerBank[questionNumber].correctAnswer,
			questionAndAnswerBank[questionNumber].wrgAns1,questionAndAnswerBank[questionNumber].wrgAns2,
			questionAndAnswerBank[questionNumber].wrgAns3];
		questionAnswerList = shuffleArray(questionAnswerList);
		$("#question").text(questionAndAnswerBank[questionNumber].questionString);
		$("#answerA").val(questionAnswerList[0]);
		$("#answerB").val(questionAnswerList[1]);
		$("#answerC").val(questionAnswerList[2]);
		$("#answerD").val(questionAnswerList[3]);
		$('label[for=answerA]').html(questionAnswerList[0]);
		$('label[for=answerB]').html(questionAnswerList[1]);
		$('label[for=answerC]').html(questionAnswerList[2]);
		$('label[for=answerD]').html(questionAnswerList[3]);
	} else {
		console.log(correctAnswerIdList);
		console.log(wrongAnswerIdList);
	}
}

function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}

function questionModalLoader(){
	$('#questionModal').modal({backdrop: 'static', keyboard: false});
}


function fullscreenVideo(){
    if($("#homepageVideo").get(0).requestFullscreen){
    	$("#homepageVideo").get(0).requestFullscreen();
    } 
    else if ($("#homepageVideo").get(0).webkitRequestFullscreen){
    	$("#homepageVideo").get(0).webkitRequestFullscreen();
    }
    else if ($("#homepageVideo").get(0).mozRequestFullScreen){
    	$("#homepageVideo").get(0).mozRequestFullScreen();
    }
    else if ($("#homepageVideo").get(0).msRequestFullscreen){
    	$("#homepageVideo").get(0).msRequestFullscreen();
    } 
}

function makeTranscriptBlank() {
	$("#transcript").text("");
	
}

function SetVolume(val)
{
    var player = document.getElementById('homepageVideo');
    console.log('Before: ' + player.volume);
    player.volume = val / 1;
    console.log('After: ' + player.volume);
}