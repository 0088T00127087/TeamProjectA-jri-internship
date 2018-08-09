var userName;
var questionAndAnswerBank;
var questionNumber = 0;
var countdown;
var correctAnswerIdList = [];
var wrongAnswerIdList = [];
var timeTakenToAnswerList = [];
var downloadTimer;

$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/introductionToPython.html");
	$("input[name='answers']").change(function(){
	    $("#submitAnswer").css("background-color","green").prop("disabled",false);
	});
});

function commenceTopic(){
	$("#commenceTopic").css("display","none");
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
				commenceTRICOnResponseResult(response);
			});
		} else {
			$("#managerApprovalRequired").css("display","inline");
			setTimeout(function(){
				document.location.href = '/frontend/pages/topics.html?username=' + userName;
			},5000);
		}
	});	
}

function commenceTRICOnResponseResult(response){
	if (response === 0){
		customisation();
		updateDatabaseAndNotifyManager();
		retrieveQuestions();
		setTimeout(function(){
			videoFunctionality();
			$("#pageContent").css("display","inline");						
		},3000);
		$("#homepageVideo").get(0).prop("volume", this.value);		
	} else if (response === 1){
		$("#navigationError").css("display","inline");
		registerFailureAndRevertToNotStarted();
		setTimeout(function(){
			document.location.href = '/frontend/pages/topics.html?username=' + userName;
		},5000);
	} else if (response === 2){
		customisation();
	//	$("#managerApprovalRequired").css("display","inline");
		$("#pageContent").css("display","inline");
		videoFunctionalityWhenCompletePrior();
	}
}

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

function videoFunctionalityWhenCompletePrior(){
	$("#homepageVideo").get(0).play();
	$('#homepageVideo').on('ended',function(){
		setTimeout(function(){
			document.location.href = '/frontend/pages/topics.html?username=' + userName;			
		},3000);
	});	
}


function submitAnswer(){
	clearInterval(downloadTimer);
	if ($("input[name='answers']:checked").val()){
		if (questionNumber <= 5){
			chosenAnswer = $('input[name=answers]:checked', '#answerList').val();
			if (chosenAnswer === questionAndAnswerBank[questionNumber].correctAnswer){
				correctAnswerIdList.push(questionAndAnswerBank[questionNumber].questId);
			} else {
				wrongAnswerIdList.push(questionAndAnswerBank[questionNumber].questId);
			} 
			timeTakenToAnswerList.push(30 - parseInt($("#timer").text()));
			$("#answerA").prop("checked",false);
			$("#answerB").prop("checked",false);
			$("#answerC").prop("checked",false);
			$("#answerD").prop("checked",false);
			$("#submitAnswer").css("background-color","lightgray").prop("disabled",true);
			if (questionNumber == 5){
				displayResultsToUser();
				submitResults();
			} else {
				setTimeout(function(){
					questionNumber++;
					loadNextQuestion();								
				},2000);
			}
		}	
	} else {
		wrongAnswerIdList.push(questionAndAnswerBank[questionNumber].questId);
		timeTakenToAnswerList.push(30);
		if (questionNumber == 5){
			displayResultsToUser();
			submitResults();
		} else {
			setTimeout(function(){
				questionNumber++;
				loadNextQuestion();				
			},2000);
		}
		
	}
}

function submitResults(){
	var videoId = "1";
	var result = passFailCheck();
	var noOfQuestionsCorrect = correctAnswerIdList.length;
	var noOfQuestionsIncorrect = wrongAnswerIdList.length;
	var timeTakenPerQuestion = timeTakenToAnswerList.toString();
	var wrongAnswerIds = wrongAnswerIdList.toString();
	$.post({
		url: "/frontend/ResultsTableServlet",
		cache: false,		
		type : "POST",
		data : {videoId: videoId,userName: userName,result:result,
			noOfQuestionsCorrect:noOfQuestionsCorrect,noOfQuestionsIncorrect:noOfQuestionsIncorrect,
			timeTakenPerQuestion: timeTakenPerQuestion, wrongAnswerIds: wrongAnswerIds}
	},function(result){
		console.log(result);
	});
}

function displayResultsToUser(){
	$("#question").css("display","none");
	$("#submitAnswer").css("display","none");
	$("#timer").css("display","none");
	$("#answerList").css("display","none");
	if (wrongAnswerIdList.length <= 1){
		$("#resultsGreeting").text("Congratulations!");
		$("#percentageFigure").text(resultCalculator());
		$("#tricResults").css("display","inline");
	} else {
		$("#resultsGreeting").text("Unlucky..");
		$("#percentageFigure").text(resultCalculator());
		$("#tricResults").css("display","inline");
	}
}

function resultCalculator(){
	var noOfCorrectAnswers = correctAnswerIdList.length;
	return ((100 * noOfCorrectAnswers) / 6).toFixed(2) + "%";
}

function loadNextQuestion(){
	if (questionNumber <= 5){
		timer();
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

function timer(){
	  var timeleft = 31;
	    downloadTimer = setInterval(function(){
	    timeleft--;
	    document.getElementById("timer").textContent = timeleft;
	    if(timeleft <= 0)
	    	submitAnswer();
	    },1000);
}

function passFailCheck(){
	if (wrongAnswerIdList.length >= 2){
		return "fail";
	} else {
		return "pass";
	}
}

function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.text(seconds);

        if (--timer < 0) {
            timer = duration;
        }
    }, 1000);
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

function SetVolume(val){
    var player = document.getElementById('homepageVideo');
    player.volume = val / 1;
}

function returnToTopics(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
}

