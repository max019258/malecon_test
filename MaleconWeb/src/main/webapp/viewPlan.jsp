<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

</head>
<body class="is-preload">

<% 
String conn_name=request.getParameter("conn_name");
String query=request.getParameter("query");

pageContext.setAttribute("conn_name",conn_name);
pageContext.setAttribute("query",query);

try{
	String nowPage_str=request.getParameter("nowPage");
	int nowPage;
	if(nowPage_str!=null){
		nowPage=Integer.parseInt(nowPage_str);
	}else{
		nowPage=1;
	}
	

	ArrayList<String> material=MaleconDb.selectAll(conn_name);
	DbMaterial dbMaterials=new DbMaterial(material.get(0),material.get(1),material.get(2),material.get(3),material.get(4),material.get(5));// Db 재료들
	
	Connection connection=DbConn.getConnection(dbMaterials);// 커넥션 가지고옴
	
	ArrayList<String> planList=DbConn.getPlan(connection,query);

	
	pageContext.setAttribute("planList",planList);
	pageContext.setAttribute("conn_name",conn_name);
	pageContext.setAttribute("query",query);
	
	
%>
<b><c:out value="연결이름 : ${conn_name} "/></b><br>
	<b><c:out value="sql: ${query}"/></b><br><br>
	
	<b>${planList.get(0)}</b>
	
	<c:set var="cnt" value="0" />
	<table>
		
		<c:forEach var="i" items="${planList}" >
			<c:set var="cnt" value="${cnt+1}"/>
			<c:set var="bar" value="|" />
			<c:set var="arr" value="${fn:split(i,bar)}" />
			<c:set var="length" value="${fn:length(arr)}" />
			
			<c:set var="tec" value="---" /> 	<!-- 맨 마지막 ---찾기 -->
			  <c:if test="${fn:startsWith(i,tec)}">
		     	<c:set var="tec_row" value="${cnt}"/><!-- 맨 마지막 ---의 row-->
		     </c:if>
			
			<c:if test="${length ne 1}">
				<tr>
					<c:forTokens var="j" items="${i}" delims="|" >
						<td nowrap>
							${j}
						</td>
					</c:forTokens>
				</tr>			
		     </c:if>
		   
		</c:forEach>
	</table>
	
	<c:set var="cnt2" value="0" />
	<c:forEach var="k" items="${planList}" >
			<c:set var="cnt2" value="${cnt2+1}"/>
			<c:if test="${cnt2 > cnt}">
				${k} <br>
			</c:if>
	</c:forEach>

<% 
}catch(Exception e){
	%>
	<b><c:out value="연결이름 : ${conn_name} "/></b><br>
	<b><c:out value="sql: ${query}"/></b><br><br>
	<% 
	out.println(e.getMessage());
}
%>
<!-- --------------------------------------------------------------------------------------------------------- -->


</body>
</html>
