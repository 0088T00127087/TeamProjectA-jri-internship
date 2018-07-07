<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<script src="/frontend/js/common/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="/frontend/js/custom/logIn.js" type="text/javascript"></script> 
<link rel="stylesheet" href="/frontend/css/base.css">

<title>LMS</title>
</head>


<body>
<button style="font-size:25px; color: red;" onclick="reAssignVideo()">Reassign Video</button>
 <video id="homepageVideo" style="margin-left:26%;margin-bottom:-4%;" width="640" height="440">
  <source src="/frontend/extra-resources/videos/toBeReversed.mp4" type="video/mp4">
</video> 

<div id="qa" style="font-size:30px;text-align:center;color:white;display:none;">
<p id="question">2 + 2 =
<span id="answer">?</span>
</p>

<div>
<button class="landingButton" onclick="sum()">
This will be the landing/log in page.
</button>
</div>
</div>
<div style="color:white;">
<p>Current Video Position:<span id="current"></span></p>
<p>Video Duration:<span id="duration"></span></p>
</div>
</body>
</html>