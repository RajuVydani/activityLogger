<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html >

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
<body>
	<h1 align="center">TimeTracker</h1>
	
	<spring:form id="loginForm" modelAttribute="loginAtt" action="authenticate" method="post">
	    <table align="center">
	        <tr>
	            <td>
	                <spring:label path="username">Username: </spring:label>
	            </td>
	            <td>
	                <%-- <spring:input path="username" name="username" id="username" /> --%>
	                <input type="text" name="username" placeholder="Enter username" required />
	            </td>
	        </tr>
	        <tr>
	            <td>
	                <spring:label path="password">Password:</spring:label>
	            </td>
	            <td>
	                <%-- <spring:password path="password" name="password" id="password" /> --%>
	                <input type="password" name="password" placeholder="Enter password" required />
	            </td>
	        </tr>
	        <tr>
	            <td></td>
	            <td align="left">	                
	                <input type="submit" name="login" value="Login" />	                
	            </td>
	        </tr>
	        <tr></tr>	  
	    </table>
	</spring:form>
</body>
</html>