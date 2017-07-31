<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html >

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<spring:url value="/web/js/common.js" var="mainJS" />

<script type="text/javascript" src="${mainJS}"></script>
</head>


<body>
	<h1 align="center">TimeTracker</h1>

	<sform:form id="loginForm" modelAttribute="loginAtt"
		action="authenticate" method="post">
		<sform:errors path="*" />
		<table align="center">
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
				<td></td>
				<td align="left"><input type="submit" name="login"
					value="Login" /></td>
			</tr>
			<tr></tr>
		</table>
	</sform:form>
</body>
</html>