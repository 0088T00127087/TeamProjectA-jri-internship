var userName;
$(document).ready(function(){
	var url = new URL(window.location.href);
	userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/home.html");

		$.get({
			url: "/frontend/HomepageInitialLoadServlet",
			cache: false,		
			type : "GET",
			data : {username: userName}
		}, function(response){
			$("#name").text(response);
		});		
});

function visitTopicsPage(){
	document.location.href = '/frontend/pages/topics.html?username=' + userName;
}

function visitSandboxPage(){
	document.location.href = '/frontend/pages/py-sandbox.html?username=' + userName;
}