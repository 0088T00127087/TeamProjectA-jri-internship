<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="/frontend/js/common/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="/frontend/js/custom/logIn.js" type="text/javascript"></script> 
<link rel="stylesheet" href="/frontend/css/base.css">
</head>

<body>
<p style="font-size: 30px; color: white">Enter 2036 (Existing User)</p>
<input type="text" maxlength="6" id="requestedUserId"></input>
<button size="15" onclick="getUserName()">Get User Name</button>
<div style="font-size: 40px; color: white" id="returnedUserName"></div>



</body>
</html>