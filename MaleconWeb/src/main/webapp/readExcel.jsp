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






	<!-- Wrapper -->
	<div id="wrapper">


		<div id="main">
			<div class='inner' id='div_reload'>
				<section>
				<div>
					
					<form action="readExcel_action.jsp" method="post">
					
					
						<div>파일경로 :	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div style="float:left ;width:70%"><input type="text" name="file_path" id="file_path"/></div>					
						<div style="float:left;margin-left:5%;width:20%"><input type="submit" value="적용" > 	
						<input type='button' value='미리보기'  onclick='viewExcel()' >	</div>			
					
					
					
					</form>
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
