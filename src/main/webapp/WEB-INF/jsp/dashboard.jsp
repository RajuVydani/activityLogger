<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeTracker</title>
</head>
<body>
	<spring:form>
		<h1>Dashboard</h1>
		<h3>You are logged in as...${loginParam.username}</h3>
		<h3>...${loginParam.password}</h3> 
		<br>
		
		<a href="/TimeTrackerWeb/">Home</a>
	</spring:form>


</body>
</html>