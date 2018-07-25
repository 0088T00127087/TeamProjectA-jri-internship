var pendingActions;
var assignmentSuccess;

$(document).ready(function(){	
//	customisation();
	checkForPendingActions();
	checkForUsersRequiringAssignment();
			if (pendingActions){
				$("#toDoDescriptor").text("Pending Actions will appear here.");
			//	displayReAssigningContent();
			} else {
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
		url: "/frontend/VideoPermissionsWebServlet",
		cache: false,	
		async: false,
		type : "GET",
		data : {videoNumber: "1"}
	}, function(approval){
		if (approval === "approvalRequired"){
			pendingActions = true;
		} else {
			pendingActions = false;
		}
	});
}

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


//function reAssignVideo(){
//	makeReAssignRequest();
//	if (assignmentSuccess){
//		$("#reassignSuccess").css("display","inline");
//		$("#reassignSuccess").animate({opacity:1},400);
//		hideReAssigningContent();
//		setTimeout(function(){
//			$("#reassignSuccess").animate({opacity:0},400);
//		},3000);
//	}
//}
//
//function makeReAssignRequest(){
//	$.post({
//		url: "/frontend/ManagerDashboardServlet",
//		cache: false,	
//		async: false,
//		type : "POST",
//		data : {videoNumber: "1"}
//	},function(result){
//		if (result === "success"){
//			assignmentSuccess = true;
//		} else {
//			assignmentSuccess = false;
//		}
//	});
//}