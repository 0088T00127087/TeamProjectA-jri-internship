var pendingActions;
var assignmentSuccess;
var userName;

$(document).ready(function(){	
//	customisation();
	
	checkForUsersRequiringAssignment();
	checkForPendingActions();
	if ($("#toDoDescriptor").css("display") !== "none"){
		$("#toDoDescriptor").text("Pending Actions will appear here.");
	}		
});

function customisation(){
	$.get({
		url: "/frontend/HomepageInitialLoadServlet",
		cache: false,		
		type : "GET",
		data : {username: userName}
	}, function(response){
		$("#name").text(response);
	});	
}

function inviteUser(){
	var emailAddress = $("#emailAddress").val();
	console.log(emailAddress);
	$.get({
		url: "http://localhost:8080/ProjectA/api/sendRegistrationInvite/" + emailAddress,
		cache: false,		
		type : "GET",
		data : {username: userName}
	}, function(response){
		if (response === ""){
			$("#invitationResult").css({"color":"green","visibility":"visible"});
			$("#invitationResult").text("Invitation Sent To " + emailAddress);
			$("#emailAddress").val("");
			$("#invitationResult").animate({opacity:1,},400);
			setTimeout(function(){
				$("#invitationResult").animate({opacity:0,},400);
				$("#invitationResult").css("visibility","hidden");
			},3400);
		} else if (response === "failure"){
			$("#invitationResult").css({"color":"red","visibility":"visible"});
			$("#invitationResult").text("Failed to send invite to " + emailAddress);
			$("#invitationResult").animate({opacity:1,},400);
			setTimeout(function(){
				$("#invitationResult").css("visibility","hidden");
			},3000);
		}
	});
	
}

function displayReAssigningContent(){
	$("#toDoDescriptor").css("display","none");
	$("#reAssignVideoOne").css("display","inline");
}

function hideReAssigningContent(){
	$("#toDoDescriptor").text("Pending Actions will appear here.");
	$("#toDoDescriptor").css("display","inline");
	$("#reAssignVideoOne").css("display","none");
}

function checkForPendingActions(){
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/retrieveAllUsersThatRequireManagerReview",
		cache: false,
		type : "GET",
	}, function(response){
		if (response[0] !== undefined){
			$("#toDoDescriptor").css("display","none");
			for (var i = 0; i < response.length;i++){
				$("<p class='userRequiringManagerAttention'>" + response[i].userName + " requires attention</p>" +
						"<button onclick='reAssignCourseToChosenUser(\""+ response[i].userName + "\")' " +
								"style='float:right;margin-right:583px;margin-top:-42px;'>Reassign Course</button>").appendTo("#usersRequiringAttention");	
			}
			$("select#usersRequiringAttention").prop('selectedIndex',0);
			$("#examineUser").prop("disabled",false);
		} else {
			$("#toDoDescriptor").css("display","inline");
		}
	});
}

function expandDetailsConcerningUser(userName){
	$("#expandOnUserThatRequiresAttention").modal();
	userName = userName;
	
}

function expandOnUserRequiringAttention(){
	var selectedUser = $("#usersRequiringAttention :selected").val();
	userName = selectedUser;
	$("#expandOnUserThatRequiresAttention").modal();
	
};

function reAssignCourseToChosenUser(userAssignment){
	var userName = userAssignment;
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/reAssignCourseToUser/" + userName + "/1/",
		cache: false,
		type : "GET",
	}, function(response){
		if (response === ""){
			$("#usersRequiringAttention").empty();
			checkForPendingActions();
		}
});
};

function checkForUsersRequiringAssignment(){
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/retrieveAllUnassignedUsers",
		cache: false,	
		async: false,
		type : "GET",
	}, function(response){
		insertResponseIntoTable(response);
	});
}

function insertResponseIntoTable(response){
	$("#userName").empty();
	for (var i=0; i < response.length;i++){
		$("<option value='" + response[i].userName + "' id='" + response[i].userName + "'>" + response[i].firstName + " " + response[i].secondName + " (" + response[i].userName + ")</option>").appendTo("#userName");
	}
	$("select#userName").prop('selectedIndex',0);
	$("select#courseName").prop('selectedIndex',0);
}

function assignCourse(){
	var selectedUser = $("#userName :selected").val();
	var selectedCourse = $("#courseName :selected").val();
	$.post({
		url: "/frontend/CourseAssignmentServlet",
		cache: false,		
		type : "POST",
		data : {courseId: selectedCourse,userName: selectedUser,status:"0",countOfManagerReview:"0",videoTrackingNo:"1"}
	},function(result){
		courseAssignmentResult(result);
	});
}

function courseAssignmentResult(result){
	console.log(result);
	checkForUsersRequiringAssignment();
}