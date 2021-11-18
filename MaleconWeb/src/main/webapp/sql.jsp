<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.gachidata.maleconDB.*"%>
<%@page import="com.gachidata.db.*"%>
<%@page import="com.gachidata.page.*"%>
<%@page import="java.sql.Connection"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>


<html>


<head>
<title>Editorial by HTML5 UP</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<script src="assets/js/script.js"></script>
</head>

<body class="is-preload">

<%
	ArrayList<String> arrConn=MaleconDb.selectConn();
	pageContext.setAttribute("arrConn",arrConn);
%>




	<!-- Wrapper -->
	<div id="wrapper">


		<div id="main">
			<div class='inner' id='div_reload'>
				<section>
				<div>
					
					<form action="sql_action.jsp" method="post">
						<div>
							<div style=width:300px; margin-left:10px">
							<label for="conn_name">연결이름</label> 
							<select id="conn_name" name="conn_name" size="1">
								<option value="">선택하세요.</option>
							<c:forEach var="i" items="${arrConn}">
								<option value="${i}">${i}</option>
								</c:forEach>
								
							</select>
							</div>
								<div > SQL :</div>
						
							<div style="margin-top:10px"><TEXTAREA id="query" NAME="query" COLS=70 ROWS=10></TEXTAREA></div>					
							<div style="margin-top:10px">
								<input type='button' value='execute'  onclick='viewTable()' >
								<input type='button' value='plan'  onclick='viewPlan()' >
							</div>
						
						</div>	
					
							
					
					
					
					</form>
					
					<hr style=width:100%>
					
					</div >
					<div id='view' style="margin-top:5%;height: 700px; overflow: auto;">
				
					</div>
				
				
				</section>

			</div>
		</div>
		<div id="sidebar">
			<%@include file="sideBar.jsp"%>
		</div>
	</div>




</body>
</html>
