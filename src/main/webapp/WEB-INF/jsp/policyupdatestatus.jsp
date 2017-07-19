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
		<h3> Policy Status ${policyStatus.policyUpdateStatus}</h3>
 
		<br>
		
		<a href="/TimeTrackerWeb/">Home</a>
	</spring:form>


</body>
</html>