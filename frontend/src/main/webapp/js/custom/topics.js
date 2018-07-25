var userName;
$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	customisation();
	getCourseProgress();
	window.history.replaceState({}, document.title, "/frontend/pages/topics.html");
});

function visitHomePage(){
	document.location.href = '/frontend/pages/home.html?username=' + userName;
}

function visitSandboxPage(){
	document.location.href = '/frontend/pages/py-sandbox.html?username=' + userName;
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


function getCourseProgress(){
	var url = "http://localhost:8080/ProjectA/course-registration/getCurrentVideo/"
	$.get({
		url: url + userName + "/1",
		cache: false,		
		type : "GET",
	}, function(response){
		if (response === "1"){
			$("#lock1").css({"visibility":"hidden"});
			$("#button1").css("background-color","blueviolet").prop("disabled",false);
			$("#s2_child1").css({"background":'url("/frontend/extra-resources/images/py1.jpg")',"background-size":"343px 218px"});
		} else if (response === "2"){
			$("#lock1").css({"visibility":"hidden"});
			$("#lock2").css({"visibility":"hidden"});
		} else if (response === "3"){
			$("#lock1").css({"visibility":"hidden"});
			$("#lock2").css({"visibility":"hidden"});
			$("#lock3").css({"visibility":"hidden"});
		}
	});	
}

function introToPythonPreRequisites(){
	$("#introToPythonPreReq").modal();
}

function beginTopicOne(){
	document.location.href = '/frontend/pages/introductionToPython.html?username=' + userName;
}
//
//$.get({
//	url: "/frontend/VideoPermissionsWebServlet",
//	cache: false,		
//	type : "GET",
//	data : {videoNumber: "1"}
//}, function(validView){
//	if (validView === "valid"){
//		$("#introVideo").css("display","inline");
//		$("#introVideo").get(0).play();
//		$("#introVideo").on("timeupdate", 
//				function(event){
//			onTrackedVideoFrame(this.currentTime, this.duration);
//		});
//		$('#introVideo').on('ended',function(){
//			adjustVideoPermissions();
//			$("#videoStateDesc").text("Please proceed to TRIC.");
//			$('#introVideo').animate({"opacity":"0"},500);
//		});	
//	} else if(validView === "videoWatched") {
//		$("#introVideo").css("display","none");
//		$("#videoStateDesc").text("Video has already been viewed, proceed to TRIC.");
//	} else if (validView === "approvalRequired"){
//		$("#introVideo").css("display","none");
//		$("#videoStateDesc").text("In order to view video, the module must be reassigned.");
//	}
//});