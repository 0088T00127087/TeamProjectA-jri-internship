var userName;
$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	$.get({
		url: "/frontend/HomepageInitialLoadServlet",
		cache: false,		
		type : "GET",
		data : {username: userName}
	}, function(response){
		$("#name").text(response);
	});	
	window.history.replaceState({}, document.title, "/frontend/pages/py-sandbox.html");
});


function visitHomePage(){
	document.location.href = '/frontend/pages/home.html?username=' + userName;
}

function visitTopicsPage(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
}

function clearArea(){
	$("#yourcode").val('');
}

