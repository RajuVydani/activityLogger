<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
	</head>
	<body>
	
		<jsp:include page="navigation.jsp"></jsp:include>
	 
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
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
		<spring:form method="POST" modelAttribute="searchAtt" action="filter" >
 <center>
<H2>Logged In As ${displayList.ManagerName}</H2>

	</center>
	<input type="Hidden" name="hcmSupervisor" id ="hcmSupervisor" value='${displayList.ManagerName}'>
	<TABLE align="center" border="0">
	<TR>
	<TD>From Date</TD>
		<TD>To Date</TD>
			<TD>Project Id</TD>
				<TD>Location</TD>
					<TD>Shift Timings</TD>
						<TD></TD>
	<TR>
	<TD><input type="date"  name="fromDate" required style="width:150px;height: 22px"/> </TD>
	<TD> <input type="date"  name="toDate" style="width:150px;height: 22px" required/></TD>
	<TD> 
	<select name="projectId" style="width:150px;height: 22px">
  <option value="">--Select--</option>
  
  	<c:if test="${not empty projectlist}">
 
			<c:forEach var="projectlistValue" items="${projectlist}">
		 
				  <option value="${projectlistValue}">${projectlistValue}</option>
			</c:forEach>
 

	</c:if>
</select>
	</TD>
	<TD> 
	<select name="location" style="width:150px;height: 22px">
  <option value="">--Select--</option>
  
  	<c:if test="${not empty locationlist}">
 
			<c:forEach var="locationlistValue" items="${locationlist}">
	 
				
				  <option value="${locationlistValue}">${locationlistValue}</option>
			</c:forEach>
 

	</c:if>
</select>
	</TD>
	<TD> 
	
<select name="shiftTimings" style="width:150px;height: 22px">
  <option value="">--Select--</option>
  
  	<c:if test="${not empty shiftTimingslist}">
 
			<c:forEach var="shiftTimingslistValue" items="${shiftTimingslist}">
 
				
				  <option value="${shiftTimingslistValue}">${shiftTimingslistValue}</option>
			</c:forEach>
 

	</c:if>
</select>
	</TD>
	<TD>
	<td>&nbsp;<input type="submit"  value="submit" style="width:150px;height: 22px" /></TD>
	</TR>






</TABLE>
	</spring:form>
	<center><a href="/TimeTrackerWeb/">Home</a></center>
<input type="hidden" name="fromdate" id ="fromdate" value='${displayList.DefaultFromDate}'>
<input type="hidden" name="todate" id ="todate" value='${displayList.DefaultToDate}'>

<div id="overall">
		<table class="table table-hover">
			<tr>

				<td>Agent Name</td>
				<td>Agent Email Id</td>
					<td>Project Id</td>
				<td>Productivity Hours</td>
				<td>Idle Hours</td>
				<td>Shift Details</td>
								<td>Location</td>
			

			</tr>
	 
			<c:forEach items="${agentOverAllList}" var="overalllist">
				<tr>

					<td>${overalllist.name}</td>
					<td><a href="#"  class="email_link" >${overalllist.emailId}</a></td>
								<td>${overalllist.projectId}</td>
					<td>${overalllist.productiveHours}</td>
					<td>${overalllist.idleHours}</td>
					<td>${overalllist.shiftTimings}</td>
						<td>${overalllist.location}</td>
		

				</tr>
			</c:forEach>
		</table>
</div>
 
 
 
		<br>


	</body>
</html>