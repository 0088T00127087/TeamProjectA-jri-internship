

$(document).ready(function(){
	$( "#logIn" ).click(function() {
		  $( "#userCredentialsEntry" ).slideToggle( "slow");
		});
	$("#password").on("keydown",function(key){
		var enterKey = 13;
		if (key.which === enterKey){
			$("#signIn").click();
		}
	});
});

function attemptSignIn(){
	var username =  $("#username").val();
	var password = $("#password").val();
	$.get({
		url: "/frontend/SignInRegistrationServlet",
		cache: false,		
		type : "GET",
		data : {username: username, password: password}
	}, function(response){
		if (response === "true"){
			document.location.href = '/frontend/pages/home.html?username=' + username;
		} else {
			console.log("Could not retrieve user.");
		}
	});
};