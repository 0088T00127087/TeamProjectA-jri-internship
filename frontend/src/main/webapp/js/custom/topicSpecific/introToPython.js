var userName;
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
					videoValidity();
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
		console.log(response);
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

function videoValidity(){
//	$.get({
//		url: "/frontend/VideoPermissionsWebServlet",
//		cache: false,		
//		type : "GET",
//		data : {videoNumber: "1"}
//	}, function(validView){
	//	if (validView === "valid"){
			$("#homepageVideo").get(0).play();
//			$("#homepageVideo").on("timeupdate", 
//					function(event){
//				onTrackedVideoFrame(this.currentTime, this.duration);
//			});
			$('#homepageVideo').on('ended',function(){
				questionModalLoader();
		//		adjustVideoPermissions();
				makeTranscriptBlank();
			});	
		//} 
//		else {
//			console.log(validView);
//		}
//	});
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