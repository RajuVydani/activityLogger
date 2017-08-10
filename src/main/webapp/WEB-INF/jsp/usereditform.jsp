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
	<h1 align="center">Add User</h1>
 
	<sform:form id="adduser" modelAttribute="adminAtt"
		action="userupdate" method="post">
	 
<sform:errors path="*" />
	<input type="hidden" name="userId" placeholder="Enter User ID"
					required  value='${displayList.currentUserId}'/>
				</td>
		<table align="center" border="0">
				<tr>
				<td><sform:label path="userId">ID: </sform:label></td>
				<td>
				${displayList.currentUserId}
				 
		 
				</td>
			</tr>
			<tr>
			<tr>
				<td><sform:label path="username">Username: </sform:label></td>
				<td>
					<%-- <sform:input path="username" name="username" id="username" /> --%>
					<input type="text" name="username" placeholder="Enter username"
					required  value='${displayList.currentUserName}'/>
				</td>
			</tr>
		 
			<tr>
				<td><sform:label path="userType">Privileges: </sform:label></td>
				<td>
				<select name="userType" style="width:150px;height: 22px" required>
			
 
  
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
 
  
  	<c:if test="${not empty locationList}">
 
			<c:forEach var="locationListValue" items="${locationList}">
	 
				
				  <option value="${locationListValue}">${locationListValue}</option>
			</c:forEach>
 

	</c:if>
</select>
				</td>
			</tr>
			 
				 
 
		</table>
		<center>
		<input type="submit"  value="Update" style="width:150px;height: 22px" />
		</center>
	</sform:form>
</body>
</html>