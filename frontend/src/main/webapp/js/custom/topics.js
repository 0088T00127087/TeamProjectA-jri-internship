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
	window.history.replaceState({}, document.title, "/frontend/pages/topics.html");
	$.get({
		url: "/frontend/VideoPermissionsWebServlet",
		cache: false,		
		type : "GET",
		data : {videoNumber: "1"}
	}, function(validView){
		if (validView === "valid"){
			$("#introVideo").css("display","inline");
			$("#introVideo").get(0).play();
			$("#introVideo").on("timeupdate", 
					function(event){
				onTrackedVideoFrame(this.currentTime, this.duration);
			});
			$('#introVideo').on('ended',function(){
				adjustVideoPermissions();
				$("#videoStateDesc").text("Please proceed to TRIC.");
				$('#introVideo').animate({"opacity":"0"},500);
			});	
		} else if(validView === "videoWatched") {
			$("#introVideo").css("display","none");
			$("#videoStateDesc").text("Video has already been viewed, proceed to TRIC.");
		} else if (validView === "approvalRequired"){
			$("#introVideo").css("display","none");
			$("#videoStateDesc").text("In order to view video, the module must be reassigned.");
		}
	});
});

function visitHomePage(){
	document.location.href = '/frontend/pages/home.html?username=' + userName;
}

function visitSandboxPage(){
	document.location.href = '/frontend/pages/py-sandbox.html?username=' + userName;
}