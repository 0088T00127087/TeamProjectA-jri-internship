var userName;
$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/introductionToPython.html");
	customisation();
	retrieveQuestions();
	videoValidity();
	$("#homepageVideo").get(0).prop("volume", this.value);
	$('#volume').on('change', function() {
		$("#homepageVideo").get(0).prop("volume", this.value);
	});
});

$(function() {
    $(document).scrollTop( $("#homepageVideo").offset().top );  
});

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
	$('#questionModal').modal({backdrop: 'static', keyboard: false})
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