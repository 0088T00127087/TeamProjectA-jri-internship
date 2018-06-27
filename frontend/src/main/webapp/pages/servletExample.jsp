<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="/lms-front-end/js/common/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="/lms-front-end/js/custom/logIn.js" type="text/javascript"></script> 
<link rel="stylesheet" href="/lms-front-end/css/base.css">
</head>

<body>

<input type="text" maxlength="6" id="requestedUserId"></input>
<button size="15" onclick="getUserName()">Get User Name</button>
<div style="font-size: 40px; color: white" id="returnedUserName"></div>



</body>
</html>