var userName;
$(document).ready(function(){
	loader();
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	getCourseCredentials();
	customisation();
	window.history.replaceState({}, document.title, "/frontend/pages/py-sandbox.html");
});

function loader(){
	$("#spinner").css("display","inline");
setTimeout(function(){
	$("#spinner").animate({opacity:0,},300);
},1500);
setTimeout(function(){
	$("#spinner").css("display","none");
	$(".container").css("display","block");
	$(".footer").css("display","block");
},1800);
setTimeout(function(){
	$(".container").css("visibility","visible").animate({opacity:1,},300);
	$(".footer").css("visibility","visible").animate({opacity:1,},300);
},2000);
}


function visitHomePage(){
	document.location.href = '/frontend/pages/home.html?username=' + userName;
}

function visitTopicsPage(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
}

function clearArea(){
	$("#yourcode").val('');
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

