<%@ page import="com.gachidata.db.DbConn"%>
<%@ page import="com.gachidata.db.DbMaterial"%>
<%@ page import="com.gachidata.map.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>

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

		<!-- Main -->
		<div id="main">
			<div class='inner'>
				<section>
					<% 
							request.setCharacterEncoding("UTF-8"); 
							String conn=request.getParameter("conn");
							String host=request.getParameter("host");
							String service=request.getParameter("service");
							String port=request.getParameter("port");
							String user=request.getParameter("user");
							String pwd=request.getParameter("pwd");
						
							DbMaterial dbMaterial=new DbMaterial(conn,host,service,port,user,pwd);// Db 재료들
						
							
							Connection connection = DbConn.getConnection(dbMaterial); //Dbconnection 이다							
							//
							//ArrayList<String> open=DbConn.getOpen(connection); // 오픈 리스트들을 가지고온다.	
							DbConn.getOpen(connection); // 오픈 리스트들을 가지고온다.
				
							
							//OpenList.addOpenMap(conn,open); //map에 추가 완료
							
							
							//Db.addUser(user); //유저 목록Map에 유저를 넣는다.
							
							//response.sendRedirect("/members"); // /목록제시하는 페이지로 이동
								%>
				</section>
			</div>
		</div>
	 <%@include file="sideBar.jsp"%>
	

	</div>


	  
</body>
</html>