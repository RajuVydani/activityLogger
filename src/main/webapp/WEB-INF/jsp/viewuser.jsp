<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html >

<html>
<head>
 
<title>Login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Dashboard</title>
		  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
 
</head>


<body>

		<jsp:include page="navigation.jsp"></jsp:include>
	 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<h1>Users List</h1>  
<center> <a href="\TimeTrackerWeb\admin">Go Back</a><br>	</center>
<table border="2" width="70%" cellpadding="2">  
<tr><th>User Id</th><th>User Name</th><th>Prvileges</th><th>Location</th><th>Access Level</th>
<th>Program ID</th>
<th>Project ID</th>
<th>Sub Project ID</th>
<th>Edit</th><th>Delete</th></tr>  
   <c:forEach var="user" items="${list}">   
   <tr>  
   <td>${user.userId}</td>  
   <td>${user.username}</td>  
   <td>${user.userType}</td>  
   <td>${user.locationId}</td>  
     <td>${user.accessLevel}</td>  
       <td>${user.programId}</td>  
         <td>${user.projectId}</td>  
           <td>${user.subProjectId}</td>  
   <td><a href="edituser?id=${user.userId}">Edit</a></td>  
   <td><a href="deleteuser?id=${user.userId}">Delete</a></td>  
   </tr>  
   </c:forEach>  
   </table>  
   <br/>  
</body>
</html>