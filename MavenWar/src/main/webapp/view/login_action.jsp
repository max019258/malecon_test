<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.gachidata.db.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
	request.setCharacterEncoding("UTF-8"); 
	String conn=request.getParameter("conn");
	String host=request.getParameter("host");
	String service=request.getParameter("service");
	String port=request.getParameter("port");
	String user=request.getParameter("user");
	String pwd=request.getParameter("pwd");

	DbConn dbconn=new DbConn()

	
	User user= new User(name,add,dept); //user 객체 가져와서 선언
	Db.addUser(user); //유저 목록Map에 유저를 넣는다.
	
	response.sendRedirect("/members"); // /sign으로 페이지이동
			%>
<%=name %>
<%=add %>
<%=dept %>
</body>
</html>