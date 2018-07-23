var auth;
$(document).ready(function(){
	var url = new URL(window.location.href);
	auth =  url.searchParams.get("auth");
	if (auth === null){
		document.location.href = '/frontend/pages/index.html';
	}
	getActivationStatus(function(response){
		if (response === "0"){
			window.history.replaceState({}, document.title, "/frontend/pages/activation.html");
			$('body').css("visibility","visible").animate({"opacity":1,},400);
			setTimeout(function(){
				$.post({
					url: "/frontend/AccountActivationServlet",
					cache: false,		
					type : "POST",
					data : {auth: auth}
				},function(result){
					if (result !== "failure"){
						accountCreatedRedirect();
					}
				});
			},100);
		} else {
			document.location.href = '/frontend/pages/index.html';
		}
	});
});

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

function accountCreatedRedirect(){
	$("#successfulCreation").css("display","inline");
	setTimeout(function(){
		document.location.href = '/frontend/pages/index.html';
	},4000);
}