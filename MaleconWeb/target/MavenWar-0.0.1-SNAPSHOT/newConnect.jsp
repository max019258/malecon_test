<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Editorial by HTML5 UP</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		
		<div id="main">
			<div class='inner'>
				<section>
					<b> &lt DB 계정 연동 &gt </b><br>
					<form action="newConnectAction.jsp" method="post">
						<table style="height:90%">
							<tr>
								<td>연결 이름 :</td>
								<td><input name="conn" type="text" /></td>
							</tr>
							<tr>
								<td>호스트 이름 :</td>
								<td><input name="host" type="text" /></td>
							</tr>
							<tr>
								<td>서비스 이름 :</td>
								<td><input name="service" type="text" /></td>
							</tr>
							<tr>
								<td>포트 :</td>
								<td><input id="spinner" name="port" type="text" /></td>
							</tr>
							<tr>
								<td>사용자 이름 :</td>
								<td><input name="user" type="text" /></td>
							</tr>
							<tr>
								<td>비밀번호 :</td>
								<td><input name="pwd" type="password" /></td>
							</tr>


						</table>
						<button type="submit">확인</button>
					</form>
				</section>
			</div>
		</div>
		  
		  	
		  	

<%-- <jsp:include page="sideBar.jsp"></jsp:include> <!--  사이드바.jsp --> --%>
		 <%--  <%@include file="sideBar.jsp"%>
 --%>
		<!-- MainFinish -->
		 <div id="sidebar">
			<%@include file="sideBar.jsp"%>
		</div>
	</div>
	



</body>
</html>