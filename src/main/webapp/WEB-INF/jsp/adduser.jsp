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
		<center> <a href="\TimeTrackerWeb\admin">Go back</a></center> 
	<h1 align="center">Add User</h1>
 
	<sform:form id="addUser" modelAttribute="adminAtt"
		action="adduserstatus" method="post">
		<sform:errors path="*" />
		<table align="center" border="0">
				<tr>
				<td><sform:label path="userId">USER ID: </sform:label></td>
				<td>
					<%-- <sform:input path="username" name="username" id="username" /> --%>
					<input type="text" name="userId" placeholder="Enter ID"
					required />
				</td>
			</tr>
			<tr>
			<tr>
				<td><sform:label path="username">Username: </sform:label></td>
				<td>
					<%-- <sform:input path="username" name="username" id="username" /> --%>
					<input type="text" name="username" placeholder="Enter username"
					required />
				</td>
			</tr>
			<tr>
				<td><sform:label path="password">Password:</sform:label></td>
				<td>
					<%-- <sform:password path="password" name="password" id="password" /> --%>
					<input type="password" name="password" placeholder="Enter password"
					required />
				</td>
			</tr>
			<tr>
				<td><sform:label path="userType">Privileges: </sform:label></td>
				<td>
				<select name="userType" style="width:150px;height: 22px" required>
			
  <option value="">--Select--</option>
  
  	<c:if test="${not empty userTypeList}">
 
			<c:forEach var="userTypelistValue" items="${userTypeList}">
	 
				
				  <option value="${userTypelistValue}">${userTypelistValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
			
			<tr>
				<td><sform:label path="locationId">Location Name: </sform:label></td>
					<td>
				<select name="locationId" style="width:150px;height: 22px" required>
  <option value="">--Select--</option>
  
  	<c:if test="${not empty locationList}">
 
			<c:forEach var="locationListValue" items="${locationList}">
	 
				
				  <option value="${locationListValue}">${locationListValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
				<tr>
				<td><sform:label path="accessLevel">Access Level</sform:label></td>
					<td>
				<select name="accessLevel" style="width:150px;height: 22px" required>
  <option value="">--Select--</option>
    <option value="all">All</option>
      <option value="program">Program</option>
          <option value="project">Project</option>
              <option value="subproject">Sub Project</option>
   
</select>
				</td>
			</tr>
				
			<tr>
				<td><sform:label path="programName">Program Name: </sform:label></td>
					<td>
				<select name="programName" style="width:150px;height: 22px" >
  <option value="">--Select--</option>
  
  	<c:if test="${not empty programList}">
 
			<c:forEach var="programListValue" items="${programList}">
	 
				
				  <option value="${programListValue}">${programListValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
			
				
			<tr>
				<td><sform:label path="projectName">Project Name: </sform:label></td>
					<td>
				<select name="projectName" style="width:150px;height: 22px" >
  <option value="">--Select--</option>
  
  	<c:if test="${not empty projectList}">
 
			<c:forEach var="projectListValue" items="${projectList}">
	 
				
				  <option value="${projectListValue}">${projectListValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
			
			
				
			<tr>
				<td><sform:label path="subProjectName">Sub Project Name: </sform:label></td>
					<td>
				<select name="subProjectName" style="width:150px;height: 22px" >
  <option value="">--Select--</option>
  
  	<c:if test="${not empty subProjectList}">
 
			<c:forEach var="subProjectListValue" items="${subProjectList}">
	 
				
				  <option value="${subProjectListValue}">${subProjectListValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
				 
 
		</table>
		<center>
		<input type="submit"  value="submit" style="width:150px;height: 22px" />
		</center>
	</sform:form>
</body>
</html>