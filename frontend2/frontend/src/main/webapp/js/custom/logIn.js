
$(document).ready(function(){
	$("#homepageVideo").get(0).play();
	$("#homepageVideo").on("timeupdate", 
		function(event){
		  onTrackedVideoFrame(this.currentTime, this.duration);
		});
	$('#homepageVideo').on('ended',function(){
		  $('#homepageVideo').animate({"opacity":"0"},500);
	    });
});

function getUserName(){
	makeServletRequest(function(response){
		$("#returnedUserName").text(response);
	})
}

function makeServletRequest(callback){
	var userId = $("#requestedUserId").val();
	var servletURL = "/frontend/TestServlet"
	
	$.ajax({
		url :servletURL,
		userId : userId,
		type : "GET",
		data : {userId: userId},
		cache : false,
		dataType : "html",
		success : function(data){
			callback(data); 
		}
	});
}

function sum(){
	var answer = (Math.floor(Math.random() * 10) + 1).toString();
	if (answer === "5"){
		$(".landingButton").prop("disabled",true);
		$("#qa").css("color","red");
	}
	$("#answer").text(answer);
}

function onTrackedVideoFrame(currentTime, duration){
    $("#current").text(currentTime);
    $("#duration").text(duration)
}