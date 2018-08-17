var pendingActions;
var assignmentSuccess;
var userName;

$(document).ready(function(){	
//	customisation();
	loader();
	checkForUsersRequiringAssignment();
	checkForPendingActions();
	if ($("#toDoDescriptor").css("display") !== "none"){
		$("#toDoDescriptor").text("Pending Actions will appear here.");
	}		
});

function loader(){
	$("#spinner").css("display","inline");
setTimeout(function(){
	$("#spinner").animate({opacity:0,},300);
},2500);
setTimeout(function(){
	$("#spinner").css("display","none");
	$(".container").css("display","block");
	$(".footer").css("display","block");
},2800);
setTimeout(function(){
	$(".container").css("visibility","visible").animate({opacity:1,},300);
	$(".footer").css("visibility","visible").animate({opacity:1,},300);
},3000);
}

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
				var userFullName = response[i].userName;
				$.get({
					url: "/frontend/HomepageInitialLoadServlet",
					async: false,
					cache: false,		
					type : "GET",
					data : {username: userFullName}
				}, function(fullName){
					$("<p class='userRequiringManagerAttention'>"+ fullName + " (" + response[i].userName + ") has failed to pass 'Introduction To Python'.</p>" +
							"<button style='font-weight:bold;background-color:#6495ed;float:right;margin-top:-42px;color:white;' onclick='reAssignCourseToChosenUser(\""+ response[i].userName + "\")' " +
									"style='float:right;margin-top:-42px;'>Reassign Course</button>").appendTo("#usersRequiringAttention");	
				});	

			}
			$("select#usersRequiringAttention").prop('selectedIndex',0);
			$("#examineUser").prop("disabled",false);
		} else {
			$("#toDoDescriptor").css("display","inline");
		}
	});
}

function getUsersFullName(userName){

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
	$("#assignCourse").prop("disabled",true);
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
		$("<option onclick=\"retrieveAvailableCoursesForSelected()\" value='" + response[i].userName + "' id='" + response[i].userName + "'>" + response[i].firstName + " " + response[i].secondName + " (" + response[i].userName + ")</option>").appendTo("#userName");
	}
	//$("select#userName").prop('selectedIndex',0);
//	$("select#courseName").prop('selectedIndex',0);
}

function retrieveAvailableCoursesForSelected(){
	var chosenUser = $("#userName :selected").val();
	$("#courseName").empty();
	$.get({
		url: "http://localhost:8080/ProjectA/course-registration/retrieveCourseProgressFor/" + chosenUser,
		cache: false,	
		async: false,
		type : "GET",
	}, function(response){
		console.log(response);
		displayRelevantTopicsForChosenUser(response);
	});
	// retrieve VideoTrackingNo and Status For UserName
}

function displayRelevantTopicsForChosenUser(response){
	if (response[0] === 2 && response[1] === 1){
		$("<option value='2' id='variables'> Python - Variables </option>").appendTo("#courseName");
	} else if (response[0] === -1 && response[1] === -1){
		$("<option value='1' id='variables'> Introduction To Python </option>").appendTo("#courseName");
	}
	$("select#courseName").prop('selectedIndex',0);
	$("#assignCourse").prop("disabled",false);
}

function assignCourse(){
	var selectedUser = $("#userName :selected").val();
	var selectedCourse = $("#courseName :selected").val();
	if (selectedCourse === "1"){
		$.post({
			url: "/frontend/CourseAssignmentServlet",
			cache: false,		
			type : "POST",
			data : {courseId: selectedCourse,userName: selectedUser,status:"0",countOfManagerReview:"0",videoTrackingNo:selectedCourse}
		},function(result){
			$("#courseName").empty();
			courseAssignmentResult(result);
		});	
	} else if (selectedCourse === "2"){
		$.get({
			url: "http://localhost:8080/ProjectA/course-registration/updateVideoTrackingNo/" + selectedUser + "/" + "2",
			cache: false,	
			async: false,
			type : "GET",
		}, function(response){
			$("#courseName").empty();
			checkForUsersRequiringAssignment();
		});
	}
}

function courseAssignmentResult(result){
	checkForUsersRequiringAssignment();
}