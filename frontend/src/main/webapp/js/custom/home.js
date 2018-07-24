var userName;
$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/home.html");
	customisation();
	getCourseCredentials();
});

function visitTopicsPage(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
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