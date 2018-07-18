	var firstName;
	var surname;
	var email;
	var username;
	var password;

function attemptRegistration(){
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
		$("#error").css("visibility","visible");
	}
}

function createAccount(){
	$.post({
		url: "/frontend/SignInRegistrationServlet",
		cache: false,		
		type : "POST",
		data : {firstName : firstName,surname: surname, email: email, username: username, password: password}
	},function(result){
		if (result.length > 0){
			$("#success").css("visibility","visible");
			setTimeout(function(){
				document.location.href = '/frontend/pages/index.html';
			},3000);
		}
	});
}