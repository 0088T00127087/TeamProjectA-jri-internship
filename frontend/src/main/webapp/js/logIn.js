function sum(){
	var answer = (Math.floor(Math.random() * 10) + 1).toString();
	if (answer === "4"){
		$(".landingButton").prop("disabled",true);
		$("#qa").css("color","red");
	}
	$("#answer").text(answer);
}