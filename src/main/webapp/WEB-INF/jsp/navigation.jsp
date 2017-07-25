<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Navigation</title>
		
		<spring:url value="/web/bootstrap-3.3.7/js/bootstrap.min.js" var="bootJS" />
		<spring:url value="/web/bootstrap-3.3.7/css/bootstrap.min.css" var="bootCSS" />
		<spring:url value="/web/jquery/jquery-3.2.1.slim.min.js" var="jquery" />
		
		<spring:url value="/policy" var="policyUpdate" />
		<spring:url value="/dashboard" var="dashboard" />
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${bootCSS}">
		<script src="${bootJS}"></script>
		<script src="${jquery}"></script>
	</head>
	<body>		
		<nav class="navbar navbar-inverse">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" href="#">TimeTracker</a>
		    </div>
		    <ul class="nav navbar-nav">
		      <li class="${dashboardActive}"><a href="${dashboard}">Dashboard</a></li>
		      <li class="${policyUpdateActive}"><a href="${policyUpdate}">Policy Update</a></li>
		    </ul>
		    <ul class="nav navbar-nav navbar-right">
		      <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
		      <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		    </ul>
		  </div>
		</nav>
	</body>
</html>