
$(document).ready(function(){
	var url = new URL(window.location.href);
	var userName =  url.searchParams.get("username");
	if (userName === null){
		document.location.href = '/frontend/pages/index.html';
	}
	window.history.replaceState({}, document.title, "/frontend/pages/home.html");
	
	setTimeout(function(){
		$.get({
			url: "/frontend/HomepageInitialLoadServlet",
			cache: false,		
			type : "GET",
			data : {username: userName}
		}, function(response){
			$("#activeUser").text(response.toUpperCase());
		});		
	},100);
});