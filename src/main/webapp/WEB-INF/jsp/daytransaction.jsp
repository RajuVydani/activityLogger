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
	
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<title>Dashboard</title>	
	</head>
	<body>
	
 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
	 

			$(function(){
			  $('.date_link').click(function(){
	 
			    var date=$(this).text(); 
			    var emailid=document.getElementById("currentemailid").value;
	 
			    var fromDate=document.getElementById("fromdate").value;
			    var toDate=document.getElementById("todate").value;
			    
			    emailid = encodeURIComponent(emailid);
			    fromDate = encodeURIComponent(fromDate);
			    toDate = encodeURIComponent(toDate);
			    date = encodeURIComponent(date);
			    
			    
			    window.location.href="/TimeTrackerWeb/idletransaction?id="+emailid+"&date="+date+"&fromdate="+fromDate+"&todate="+toDate;
			    var date =$(this).text();
		 
		 

			/*    $.ajax({
			    	url: "/TimeTrackerWeb/transaction",
			    	data: JSON.stringify(json),
			    	type: "POST",

			    	beforeSend: function(xhr) {
			    		xhr.setRequestHeader("Accept", "application/json");
			    		xhr.setRequestHeader("Content-Type", "application/json");
			    	},
			    	success: function(response) {
			    	  		alert(response);
			    	}
			    });*/

		 
			  });
			});
		
		
		</script>
		<spring:form>
 
 
<input type="hidden" name="currentemailid" id ="currentemailid" value='${displayList.DefaultAgentEmailId}'>
<input type="hidden" name="currentdate" id ="currentdate" value='${displayList.DefaultCurrentDate}'>
<input type="hidden" name="fromdate" id ="fromdate" value='${displayList.DefaultFromDate}'>
<input type="hidden" name="todate" id ="todate" value='${displayList.DefaultToDate}'>
<div id="daywise">
<center>
<H2>Daily Report</H2>
<H3>Agent Email ID : ${displayList.DefaultAgentEmailId}</H3>
<H3>${displayList.DefaultFromDate} - ${displayList.DefaultToDate}</H3>
	</center>
		<table  class="table table-hover">
			<tr>

				<td>DATE</td>
				<td>PROD HRS</td>
				<td>IDLE HRS</td>
				<td>LOGIN TIME</td>
				<td>LOGOUT TIME</td>
				<td>SHIFT TIMINGS</td>

			</tr>
			<c:forEach items="${agentDayWiseList}" var="dayWiseList">
				<tr>

					<td><a href="#" class="date_link" >${dayWiseList.DATE}</a></td>

					<td>${dayWiseList.productiveHours}</td>
					<td>${dayWiseList.idleHours}</td>
					<td>${dayWiseList.loginTime}</td>
					<td>${dayWiseList.logoutTime}</td>
					<td>${dayWiseList.shiftTimings}</td>


				</tr>
			</c:forEach>
		</table>
		</div>
 
		<br>
 
	</spring:form>
	</body>
</html>