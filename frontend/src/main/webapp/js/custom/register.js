	var firstName;
	var surname;
	var email;
	var username;
	var password;
	var auth;

$(document).ready(function(){
	var url = new URL(window.location.href);
	auth =  url.searchParams.get("auth");
	if (auth === null){
		document.location.href = '/frontend/pages/index.html';
	}
	getActivationStatus(function(response){
		if (response !== "0"){
			document.location.href = '/frontend/pages/index.html';
		}
	});
});	
	
function attemptRegistration(){
	$("#success").text("Loading..");
	$("#success").css("visibility","visible");
	firstName = $("#firstName").val().trim();
	surname = $("#surname").val().trim();
	email = $("#email").val().trim();
	username = $("#username").val().trim();
	password = $("#password").val().trim();
	var accountDetails = [firstName,surname,email,username,password];
	var incorrectEntry = 0;
	accountDetails.forEach(function(input){
		if (input.length < 1){
			incorrectEntry++;
		}
	});
	if (incorrectEntry === 0){
		$("#error").css("visibility","hidden");
		createAccount();
	} else {
		$("#success").css("visibility","hidden");
		$("#error").css("visibility","visible");
	}
}


function getActivationStatus(callback){
	$.ajax({
		url: "/frontend/AccountActivationServlet",
		cache: false,	
		async: false,
		data : {auth: auth},
		success: function (data){
			callback(data);
		}
	});
}


function createAccount(){
	getActivationStatus(function(response){
		if (response === "0"){
			$.post({
				url: "/frontend/SignInRegistrationServlet",
				cache: false,		
				type : "POST",
				data : {firstName : firstName,surname: surname, email: email, username: username, password: password, auth: auth}
			},function(result){
				if (result !== "failure"){
					$("#success").text("Account Created! Navigating back to the Home Page.");
					$("#success").css("visibility","visible");
					setTimeout(function(){
						document.location.href = '/frontend/pages/index.html';
					},4000);
				}
			});
		}
	});
}