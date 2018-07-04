<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="/lms-front-end/js/common/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="/lms-front-end/js/custom/logIn.js" type="text/javascript"></script> 
<link rel="stylesheet" href="/frontend/css/base.css"> 
</head>

<body>
<p style="font-size: 30px; color: white">Enter 2036 (Existing User)</p>
<input type="text" maxlength="6" id="requestedUserId"></input> <!-- text input box, identifer for the value set to Requested user ID -->
<button size="15" onclick="getUserName()">Get User Name</button> <!-- button, when clicked run the getUserName() function in logIn.js ,knows to go to logIn.js because of script tag at 
																	top of page   --> 
<div style="font-size: 40px; color: white" id="returnedUserName"></div> <!-- inserts the value returnedUserName which is returned by the getUserName() function in logIn.js -->

<!-- example: enter 2036, click button, getUserName() called in logIn.js -->

</body>
</html>