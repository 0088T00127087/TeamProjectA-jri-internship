var userName;
$(document).ready(function(){
	loader();
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	customisation();
	getCourseCredentials();
	//getCourseProgress();
	window.history.replaceState({}, document.title, "/frontend/pages/userDash.html");
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

function visitTopicsPage(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
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


function getCourseCredentials(){
	var url = "http://localhost:8080/ProjectA/course-registration/getCurrentCourse/"
	$.get({
		url: url + userName,
		cache: false,		
		type : "GET",
	}, function(response){
		if (response.length > 0){
			$("#pythonBeginners").css({"cursor":"pointer","pointer-events":"auto","background-color":"white"})
		} else {
			$("#pythonBeginners").css({"cursor":"not-allowed",})
		}
	});	
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