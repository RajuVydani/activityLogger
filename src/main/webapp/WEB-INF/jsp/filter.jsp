<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.automation.vo.Agent"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Dashboard</title>	
	</head>
	<body>
	
		<jsp:include page="navigation.jsp"></jsp:include>
	 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
	 

			$(function(){
			  $('.email_link').click(function(){
			    alert($(this).text());
			    var emailid=$(this).text(); 
			    
			    var fromDate=document.getElementById("fromdate").value();
			    var toDate=document.getElementById("todate").value();
			    alert("======");
			    alert("sd"+emailid);
			    window.open("/TimeTrackerWeb/daytransaction?id="+emailid+"&fromdate="+fromDate+"&todate"+toDate);
			    var date =$(this).text();
		 
	 
		 
			  });
			});
		
		
		</script>
		<spring:form method="POST" modelAttribute="searchAtt" action="filter" >
 
<input type="date"  name="fromDate" required/>
<input type="date"  name="toDate" required/>
<input type="submit"  value="submit"/>
	</spring:form>
<input type="text" name="fromdate" id ="fromdate" value='${displayList.DefaultFromDate}'>
<input type="text" name="todate" id ="todate" value='${displayList.DefaultToDate}'>
<div id="overall">
		<table border="1">
			<tr>

				<td>Agent Name</td>
				<td>Agent Email Id</td>
				<td>Productivity Hours</td>
				<td>Idle Hours</td>
				<td>Shift Details</td>
				<td>Project Id</td>

			</tr>
	 
			<c:forEach items="${agentOverAllList}" var="overalllist">
				<tr>

					<td>${overalllist.name}</td>
					<td><a href="#"  class="email_link" >${overalllist.emailId}</a></td>
					<td>${overalllist.productiveHours}</td>
					<td>${overalllist.idleHours}</td>
					<td>${overalllist.shiftTimings}</td>
					<td>${overalllist.projectId}</td>

				</tr>
			</c:forEach>
		</table>
</div>
 
 
 
		<br>
		<a href="/TimeTrackerWeb/">Home</a>

	</body>
</html>