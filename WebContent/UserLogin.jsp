<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excess Food Management System</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" media="all">
</head>
<body>
<div class="main">
	<p class="sign" align="center">User Login</p>
		<form class="form1" action="<%=request.getContextPath()%>/UserLoginServlet" method="post">
			<input class="un" align="center" type="text" name="email" placeholder="Email" required>
			<input class="pass" align="center" type="password" name="password" placeholder="Password" required> 
			<input type="submit" class="submit" align="center" value="Submit" />
		</form><br>
		<a href="UserRegister.jsp" class="userlogin">User Register</a> <br>
		<!-- <a href="CreateOrder.jsp" class="userlogin">CreateOrder</a> -->
		
		<div align="center">
			<h6 style="color: red">${errorMessage}</h6>
		</div> 
</div>

</body>
</html>