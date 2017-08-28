<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.automation.model.Admin"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<jsp:include page="navigation.jsp"></jsp:include>

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
	 

			$(function(){
			  $('.email_link').click(function(){
			 
			    var emailid=$(this).text(); 
			    
			    var fromDate=document.getElementById("fromdate").value;
			    var toDate=document.getElementById("todate").value;
			    var width  = 1000;
		        var height = 800;
			    var x = screen.width/2 - 700/2;
			    var y = screen.height/2 - 450/2;
			    var left = (window.screen.width / 2) - ((width / 2) + 10);
			    var top = (window.screen.height / 2) - ((height / 2) + 50);
			    
			    emailid = encodeURIComponent(emailid);
			    fromDate = encodeURIComponent(fromDate);
			    toDate = encodeURIComponent(toDate);
 
			    
			    
			    
                var url="/TimeTrackerWeb/daytransaction?id="+emailid+"&fromdate="+fromDate+"&todate="+toDate;
			window.open(url,'Time Tracker',
			    "status=no,height=" + height + ",width=" + width + ",resizable=yes,left="
			    + left + ",top=" + top + ",screenX=" + left + ",screenY="
			    + top + ",toolbar=no,menubar=no,scrollbars=no,location=no,directories=no");
		 
		 
	 
		 
			  });
			});
		
		
		</script>
	<spring:form method="POST" action="filter">
		<center>
			<H2>Logged In As ${displayList.loggedInUserId}</H2>

		</center>
 
		<input type="text" name="agentCount" id="agentCount"
			value='${displayList.agentCount}'>
		<input type="text" name="loggedInUserId" id="loggedInUserId"
			value='${displayList.loggedInUserId}'>
		<input type="text" name="viewOnly" id="viewOnly"
			value='${displayList.viewOnly}'>
		<input type="text" name="pdfExport" id="pdfExport"
			value='${displayList.pdfExport}'>
		<input type="text" name="csvExport" id="csvExport"
			value='${displayList.csvExport}'>
		<input type="text" name="excelExport" id="excelExport"
			value='${displayList.excelExport}'>
					<input type="text" name="printOption" id="printOption"
			value='${displayList.printOption}'>
					<input type="text" name="prviledgedId" id="prviledgedId"
			value='${displayList.prviledgedId}'>
 
	</spring:form>
	<center>
		<a href="/TimeTrackerWeb/">Home</a>
	</center>

	${activitydetails}
	<div id="overall">
		<table class="table table-hover">
			<tr>

				<td>Prod Hrs</td>
				<td>Idle Hrs</td>
				<td>Prd Avg</td>
				<td>Idle Avg</td>
				<td>No Of Agents</td>



			</tr>

			<c:forEach items="${activitydetails}" var="activitydetailsList">
				<tr>

					<td>${activitydetailsList.prodSum}</td>

					<td>${activitydetailsList.idleSum}</td>
					<td>${activitydetailsList.prodAvg}</td>
					<td>${activitydetailsList.idleAvg}</td>
					<td>${activitydetailsList.agentCount}</td>



				</tr>
			</c:forEach>
		</table>
	</div>



	<br>


</body>
</html>