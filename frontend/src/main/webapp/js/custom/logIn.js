
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
				adjustVideoPermissions("watched");
				$('#homepageVideo').animate({"opacity":"0"},500);
			});	
		} else {
			console.log(validView);
		}
	});
});

function adjustVideoPermissions(change){
	$.post({
		url: "/frontend/VideoPermissionsWebServlet",
		cache: false,		
		type : "POST",
		data : {videoNumber: "1"}
	},function(result){
		console.log(result);
	});
}

function getUserName(){
	makeServletRequest(function(response){ //calls the function makeServletRequest, a function; function(response){$("#returnedUserName").text(response);}); is pass and will be called later
											// later by the callback function in the success parameter of the ajax request
		$("#returnedUserName").text(response); // $ is a jQuery element selector
	})
}

function makeServletRequest(callback){
	var userId = $("#requestedUserId").val(); //get the value from the inputbox on servletExample.jsp
	var servletURL = "/frontend/TestServlet" 
	
	$.ajax({ //jquery ajax request
		url :servletURL, //set the target of the get request
		userId : userId, //the value userId = userId, 
		type : "GET", //the type of request to send, can be post or get
		data : {userId: userId}, //the data to send to the server --> Question: why is this the same as line 26, does this set what value the server needs to look for? and 
		// how do you send multiple values
		cache : false,
		dataType : "html", //telling it that is is a html page the source is?
		success : function(data){ //the function to run if the request is sucessful
			callback(data); // run the fuction that is passed by the makeServletRequest function
		} //next step go to TestServlet.java
		//when you get back from TestServlet.java, as it is a success the function in the success : ajax option (parameter?) is run.
		//this function takes the value from the response and inserts it where the CSS id "#returnedUserName" is on servletExample.jsp
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