
$(document).ready(function(){
	$.get({
		url: "/frontend/VideoPermissionsWebServlet",
		cache: false,		
		type : "GET",
		data : {videoNumber: "1"}
	}, function(validView){
		if (validView === "valid"){
			$("#homepageVideo").get(0).play();
			$("#homepageVideo").on("timeupdate", 
					function(event){
				onTrackedVideoFrame(this.currentTime, this.duration);
			});
			$('#homepageVideo').on('ended',function(){
				adjustVideoPermissions();
				$('#homepageVideo').animate({"opacity":"0"},500);
			});	
		} else {
			console.log(validView);
		}
	});
});

function adjustVideoPermissions(){
	$.post({
		url: "/frontend/VideoPermissionsWebServlet",
		cache: false,		
		type : "POST",
		data : {videoNumber: "1"}
	},function(result){
		console.log(result);
	});
}

function reAssignVideo(){
	$.post({
		url: "/frontend/ManagerDashboardServlet",
		cache: false,		
		type : "POST",
		data : {videoNumber: "1"}
	},function(result){
		console.log(result);
	});
}

function onTrackedVideoFrame(currentTime, duration){
    $("#current").text(currentTime);
    $("#duration").text(duration)
}