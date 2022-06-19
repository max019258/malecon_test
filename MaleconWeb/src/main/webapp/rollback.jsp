<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gachidata.db.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	 <%
	String conn= (String)session.getAttribute("conn_name");
	String open= (String)session.getAttribute("open_name");
	String table= (String)session.getAttribute("table_name");
	
	
	Connection connection =(Connection)session.getAttribute(conn);
	 connection.rollback();
	 session.setAttribute(conn,connection);
	
	 	out.println("<script>alert('롤백 되었습니다.');");
		out.println(  "window.location = 'showTable.jsp?i="+conn+"&j="+open+"&k="+table+"'"  ); 
		out.println("</script>");
	
		
		
		//request.setAttribute("connection_commit",connection);
	%>
	<%-- <jsp:forward page="showTable.jsp?i=${conn}&j=${open}&k=${table}"/> --%>
			
	row 입력값:  ${rowCount}
 	column 입력값:  ${colCount}
 	host : ${host}
 	
	
			<!-- 오래된 테이블 크기 끝-->



</body>
</html>