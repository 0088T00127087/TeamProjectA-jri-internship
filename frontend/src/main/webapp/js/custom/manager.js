var pendingActions;
var assignmentSuccess;

$(document).ready(function(){
	checkForPendingActions();
			if (pendingActions){
				displayReAssigningContent();
			} else {
				$("#toDoDescriptor").text("Pending Actions will appear here.");
			}			
});

function displayReAssigningContent(){
	$("#toDoDescriptor").css("display","none");
	$("#reAssignVideoOne").css("display","inline");
}

function hideReAssigningContent(){
	$("#toDoDescriptor").text("Pending Actions will appear here.");
	$("#toDoDescriptor").css("display","inline");
	$("#reAssignVideoOne").css("display","none");
}

function reAssignVideo(){
	makeReAssignRequest();
	if (assignmentSuccess){
		$("#reassignSuccess").css("display","inline");
		$("#reassignSuccess").animate({opacity:1},400);
		hideReAssigningContent();
		setTimeout(function(){
			$("#reassignSuccess").animate({opacity:0},400);
		},3000);
	}
}

function makeReAssignRequest(){
	$.post({
		url: "/frontend/ManagerDashboardServlet",
		cache: false,	
		async: false,
		type : "POST",
		data : {videoNumber: "1"}
	},function(result){
		if (result === "success"){
			assignmentSuccess = true;
		} else {
			assignmentSuccess = false;
		}
	});
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