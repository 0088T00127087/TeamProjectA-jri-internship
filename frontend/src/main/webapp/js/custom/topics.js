var userName;
$(document).ready(function(){
	loader();
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	customisation();
	getCourseProgress();
	window.history.replaceState({}, document.title, "/frontend/pages/topics.html");
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
},2800);
setTimeout(function(){
	$(".container").css("visibility","visible").animate({opacity:1,},300);
	$(".footer").css("visibility","visible").animate({opacity:1,},300);
},3000);
}


function visitHomePage(){
	document.location.href = '/frontend/pages/home.html?username=' + userName;
}

function visitSandboxPage(){
	document.location.href = '/frontend/pages/py-sandbox.html?username=' + userName;
}

function visitUserDashboardPage(){
	document.location.href = '/frontend/pages/userDash.html?username=' + userName;
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
			$.get({
			url: "http://localhost:8080/ProjectA/course-registration/getCurrentStatus/" + userName,
			cache: false,		
			type : "GET",
		}, function(response){
			if (response === 2){
				$("#complete1").css("display","block");
				$("#topicNotifier").css("display","block");
			}
		});	
			$("#lock1").css({"visibility":"hidden"});
			$("#button1").css("background-color","blueviolet").prop("disabled",false);
			$("#s2_child1").css({"background":'url("/frontend/extra-resources/images/pygif.gif")',"background-size":"325px 218px"});
		} else if (response === "2"){
			$("#complete1").css("display","block");
			$("#lock1").css({"visibility":"hidden"});
			$("#button1").css("background-color","blueviolet").prop("disabled",false);
			$("#button2").css("background-color","blueviolet").prop("disabled",false);
			$("#s2_child1").css({"background":'url("/frontend/extra-resources/images/pygif.gif")',"background-size":"325px 218px"});
			$("#s3_child1").css({"background":'url("/frontend/extra-resources/images/pygif2.gif")',"background-size":"570px 218px"});
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