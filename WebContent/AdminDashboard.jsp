<%@ page import="com.nxtgio.model.AdminModel" %>
<%@ page import="com.nxtgio.model.AdminDashboardModel, java.util.List;" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style2.css">
<link rel="stylesheet" href="css/style12.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link href="css/icofont/icofont.min.css" rel="stylesheet">
<style>


</style>
<title>Excess Food Management System</title>
<script src="
https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js">
</script>
</head>
<body class="j-light-grey">

 <% AdminModel adminmodel = (AdminModel)request.getAttribute("adminModelStr"); %>
 <% List<AdminDashboardModel> adminDashboardModel = (List<AdminDashboardModel> )request.getAttribute("adminDashboardModel"); %>
 <%-- <p><%= adminmodel.getUsername() %></p>
<p><%= adminmodel.getEmail() %></p> --%>
<!-- Top container -->
<div class="j-bar j-top j-white j-large" style="z-index:4">
  <button class="j-bar-item j-button j-hide-large j-hover-none j-hover-text-light-grey1" onclick="j_open();"><i class="fa fa-bars"></i>Menu</button>
  <span class="j-bar-item j-left"><a href="#" style="text-decoration:none;">Excess Food Management System</a></span>
  <span class="j-bar-item j-right"><a href="<%=request.getContextPath() %>/AdminLogout" style="text-decoration:none;">Logout</a></span>
</div>

<!-- Sidebar/menu -->
<nav class="j-sidebar j-collapse j-blue-jyo j-animate-left" style="z-index:3;width:350px;" id="mySidebar"><br>
  <div class="j-container j-row">
    <div class="j-col s8 j-bar">
    
      <span><i class="fa fa-user j-large"></i> Welcome, <strong style="text-transform: uppercase;"><%= adminmodel.getFirst_name() %> <%= adminmodel.getLast_name() %></strong></span><br>
    </div>
  </div>
  <hr>
  <div class="j-container">
    <h4><strong><i class="icofont-lunch j-padding j-xlarge"></i>List Of Orders</strong></h4>
  </div>
  <div class="j-bar-block">
    <a href="#" class="j-bar-item j-button j-padding-16 j-hide-large j-light-grey1 j-hover-black" onclick="j_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>Close Menu</a>
  <table>
<%
    for(int i=0; i<adminDashboardModel.size(); i++){%>
        <tr>
        <td><strong><%= adminDashboardModel.get(i).getFirst_name() + " " + adminDashboardModel.get(i).getLast_name() %></strong>
        <br><span class="j-left">Order Id = <%= adminDashboardModel.get(i).getOid() %></span>
        <span class="j-left">Requirement: <%= adminDashboardModel.get(i).getOrder_details() %></span>
        <span class="j-left">Status: <%= adminDashboardModel.get(i).getStatus() %></span>
		<span class="j-left">Created On: <%= adminDashboardModel.get(i).getCreated_on() %></span>
        <h6 class="j-left" style="text-transform: uppercase;">User Details: <%= adminDashboardModel.get(i).getDescription() %></h6>
            
            </td>            
        </tr>
      <%}%>
</table>
  </div>
</nav>
<!-- Overlay effect when opening sidebar on small screens -->
<div class="j-overlay j-hide-large j-animate-opacity" onclick="j_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="j-main" style="margin-left:350px;margin-top:43px;">

  <!-- Header -->
  <header class="j-container" style="padding-top:22px">
    <h5><b><i class="fa fa-dashboard j-xlarge"></i> <%= adminmodel.getDescription() %> Dashboard</b></h5>
   <%--  <div class="j-left "><%= (new java.util.Date()).toLocaleString()%></div> --%>
  </header>
<div class="main">
<form class="form1" action="<%=request.getContextPath()%>/AdminDashboardServlet" method="post">
<p class="sign" align="center">Update Status </p>
  	<input class="un" align="left" type="text" name="oid" placeholder="Enter order id" required >
	<select class="un" name="status" required>
	<option  class="un">Select Status</option>
	<option  class="un">Ready </option>
	<option  class="un">Pending</option>
	<option  class="un">Completed </option>
	</select>
	<input type="submit" class="submit" align="center" value="Update Status" />
</form>
</div>

<div align="center">
	<h6 style="color: red">${UIMessage}</h6>
</div>

<h6 style="color:red">${SuccessMessage}</h6>     
  <!-- Footer -->
  <footer class="j-container j-padding-16 j-light-grey">
    <div class="copyright pull-right">
          &copy; <script>document.write(new Date().getFullYear())</script>, made with <i class="fa fa-heart heart"></i> by <a href="https://www.nxtgio.com/">nxtGIO Technologies LLP</a>
        </div>
  </footer>

  <!-- End page content -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function j_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function j_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>
</body>
</html>