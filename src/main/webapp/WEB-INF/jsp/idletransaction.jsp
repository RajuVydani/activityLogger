<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.automation.vo.Agent"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Time Tracker</title>	
	</head>
	<body>
	
 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
	 

			$(function(){
			  $('.date_link').click(function(){
 
			    var emailid=document.getElementById("currentemailid").value;
		 
			    var fromDate=document.getElementById("fromdate").value;
			    var toDate=document.getElementById("todate").value;
			    window.location.href="/TimeTrackerWeb/daytransaction?id="+emailid+"&fromdate="+fromDate+"&todate="+toDate;
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
 
		
		<div id="transaction">
	<CENTER>
		<H2>Idle Timings Report</H2>
	 
<H3>Agent Email ID : ${displayList.DefaultAgentEmailId}</H3>
<H3> ${displayList.DefaultCurrentDate}</H3>
		</CENTER>
		<table align="center">
		<TR>
							<td><a href="#" class="date_link" >Go back</a></td>
				</TR>				
							</table>
		<table  class="table table-hover"  width="800">
			<tr>

				<td style="width:20%">FROM TIME</td>
				<td style="width:20%">TO TIME</td>
				<td style="width:10%">DURATION</td>
				<td style="width:40%">WEBSITE USED</td>


			</tr>
			<c:forEach items="${agentTransactionList}" var="transactionList">
				<tr>

					<td>${transactionList.idleFrom}</td>

					<td>${transactionList.idleTo}</td>

					<td>${transactionList.idleHours}</td>
					<td>${transactionList.websitesVisited}</td>



				</tr>
			</c:forEach>
		</table>
			</div>
		<br>
	 
	</spring:form>
	</body>
</html>