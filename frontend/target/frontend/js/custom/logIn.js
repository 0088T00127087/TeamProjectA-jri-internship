function sum(){
	var answer = (Math.floor(Math.random() * 10) + 1).toString();
	if (answer === "5"){
		$(".landingButton").prop("disabled",true);
		$("#qa").css("color","red");
	}
	$("#answer").text(answer);
}

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

function onTrackedVideoFrame(currentTime, duration){
    $("#current").text(currentTime);
    $("#duration").text(duration)
}