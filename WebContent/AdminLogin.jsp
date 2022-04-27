<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Excess Food Management System</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" media="all">
</head>
<body>


<div class="main">
    	<p class="sign" align="center">Service Login</p>
    	<form class="form1" action="<%=request.getContextPath()%>/AdminLoginServlet" method="post">
	      <input class="un" align="center" type="text" name="email" placeholder="Email" required>
	      <input class="pass" align="center" type="password" name="password" placeholder="Password" required>
	      <input type="submit" class="submit" align="center" value="Submit" />     
        </form><br>  
        <a href="AdminRegister.jsp" class="register" >Register Service</a><br> 
        <!-- <a href="UserRegister.jsp" class="register" >User Register</a><br>
        <a href="UserLogin.jsp" class="register" >User Login</a>  -->
  <div align="center">
  <h6 style="color:red">${errorMessage}</h6>
  </div>       
</div>

</body>
</html>