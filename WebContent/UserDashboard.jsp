<%@ page import="com.nxtgio.model.UserModel" %>
<%@ page import="com.nxtgio.model.UserDashboardModel, java.util.List;" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style2.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/icofont/icofont.min.css" rel="stylesheet">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
table {
  border-collapse: separate;
}
th, td {
  border: 1px solid #000;
  padding: 8px;
  padding: 15px;
  border-spacing: 15px;  
}
table th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #33929F;
  color: white;
}
table {
  width: 100%;
}
table{
  border: 0px solid #ddd;
  padding: 8px;
  padding: 15px;
  border-spacing: 15px; 
}
/* table tr:nth-child(odd){background-color: #33929F;} */
table tr:hover {background-color: #A0C3C8;}

</style>
<title>Excess Food Management System</title>
<script src="
https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js">
</script>
</head>
<body class="j-light-grey">
 <% UserModel usermodel = (UserModel)request.getAttribute("userModelStr"); %>

<!-- Top container -->
<div class="j-bar j-top j-white j-large" style="z-index:4">
  <button class="j-bar-item j-button j-hide-large j-hover-none j-hover-text-light-grey1" onclick="j_open();"><i class="fa fa-bars"></i>Menu</button>
  <span class="j-bar-item j-left"><a href="#" style="text-decoration:none;">Excess Food Management System</a></span>
  <span class="j-bar-item j-right"><a href="<%=request.getContextPath() %>/UserLogout" style="text-decoration:none;">Logout</a></span>
</div>

<!-- Sidebar/menu -->
<nav class="j-sidebar j-collapse j-blue-jyo j-animate-left" style="z-index:3;width:320px;" id="mySidebar"><br>
  <div class="j-container j-row">
    <div class="j-col s8 j-bar">
    
      <span><i class="fa fa-user j-large"></i> Welcome, <strong style="text-transform: uppercase;"><%= usermodel.getUsername() %></strong></span><br>
    </div>
  </div>
  <hr>
  <div class="j-container">
    <h4><strong><i class="icofont-lunch j-padding j-xlarge"></i>List Of Orders</strong></h4>
  </div>
  <div class="j-bar-block">
    <a href="#" class="j-bar-item j-button j-padding-16 j-hide-large j-light-grey1 j-hover-black" onclick="j_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>Close Menu</a>
  </div>
</nav>
<!-- Overlay effect when opening sidebar on small screens -->
<div class="j-overlay j-hide-large j-animate-opacity" onclick="j_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="j-main" style="margin-left:310px;margin-top:43px;">

  <!-- Header -->
  <header class="j-container" style="padding-top:22px">
    <h5><b><i class="fa fa-dashboard j-xlarge"></i> My Dashboard</b></h5>
   <%--  <div class="j-left "><%= (new java.util.Date()).toLocaleString()%></div> --%>
  </header>
  <div class="j-row-padding j-margin-bottom">
  <div class="j-quarter j-right">
      <div class="j-container j-blue-jyo">
        <div class="j-left "><i class="icofont-lunch j-xxlarge"></i>
        <a href="CreateOrder.jsp" class="userlogin">CreateOrder</a></div>        
      </div>
    </div>
    </div>

<% List<UserDashboardModel> userDashboardModel = (List<UserDashboardModel> )request.getAttribute("userDashboardModel"); %>
<div>
<table>
<%
    for(int i=0; i<userDashboardModel.size(); i++){%>
        <tr>
        <% if(userDashboardModel.get(i).getStatus().equalsIgnoreCase("pending")){ %>
        <td  class="btn"><strong><%= userDashboardModel.get(i).getFirst_name() + " " + userDashboardModel.get(i).getLast_name() %></strong><br><%= userDashboardModel.get(i).getOrder_details() %>
            <span style="color: red;" class="j-right"><strong><%= userDashboardModel.get(i).getStatus() %></strong></span><br>
            <span class="j-left"><%= userDashboardModel.get(i).getCreated_on() %></span>
            </td>
        <% }else if(userDashboardModel.get(i).getStatus().equalsIgnoreCase("ready")){ %>
        <td class="btn"><strong><%= userDashboardModel.get(i).getFirst_name() + " " + userDashboardModel.get(i).getLast_name() %></strong><br><%= userDashboardModel.get(i).getOrder_details() %>
            <span style="color:green" class="j-right"><strong><%= userDashboardModel.get(i).getStatus() %></strong></span><br>
            <span class="j-left"><%= userDashboardModel.get(i).getCreated_on() %></span>
            </td>
        <% }else if(userDashboardModel.get(i).getStatus().equalsIgnoreCase("completed")){ %>
        <td class="btn"><strong><%= userDashboardModel.get(i).getFirst_name() + " " + userDashboardModel.get(i).getLast_name() %></strong><br><%= userDashboardModel.get(i).getOrder_details() %>
            <span style="color: blue;" class="j-right"><strong><%= userDashboardModel.get(i).getStatus() %></strong></span><br>
            <span class="j-left"><%= userDashboardModel.get(i).getCreated_on() %></span>
            </td>
       <% } %>            
        </tr>
      <%}%>
</table>
</div>
  <h6 style="color:red">${SuccessMessage}</h6>     
  <!-- Footer -->
  <footer class="j-container j-padding-16 j-light-grey">
    <h4>FOOTER</h4>
    <p>Powered by <a href="https://www.nxtgio.com/" target="_blank">nxtGIO Technologies LLP</a></p>
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
