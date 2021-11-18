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

	Object table=DbConn.executeSQL(connection, query,nowPage);
	
	pageContext.setAttribute("conn_name",conn_name);
	pageContext.setAttribute("query",query);
	pageContext.setAttribute("table",table);

	
	boolean int_able;
	try{
		int temp_int=(int)table;
		int_able=true;
	}catch(Exception e){
		int_able=false;
		HashMap tableList=(HashMap<String,Object>)table;
		pageContext.setAttribute("tableList",tableList.get("tableList"));
		pageContext.setAttribute("paging",tableList.get("paging"));
	}
	pageContext.setAttribute("int_able",int_able);
%>
<c:choose>
	<c:when test="${int_able}">
	<b><c:out value="연결이름 : ${conn_name} "/></b><br>
		<b><c:out value="sql: ${query}"/></b><br><br>
		<c:out value="${table} 개의 행이 바뀌었습니다."/>
	</c:when>
	<c:otherwise>
	<b><c:out value="연결이름 : ${conn_name} "/></b><br>
		<b><c:out value="sql: ${query}"/></b><br><br>
		<table>
				<c:forEach var="i" items="${tableList}">
					<tr>
						<c:forEach var="j" items="${i}">
							<td nowrap>
								${j}
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
		</table>
		<%
									String url = request.getRequestURL().toString(); //url 가져오기
								//System.out.println(url);
								pageContext.setAttribute("url", url);
								%>
<div style="float:left; width: 40% ;padding-top: 2% ;margin-bottom:40px;text-align:center">

									<c:url var="action" value="${url}"  />
									<c:if test="${paging.prev}">
										<a
											href="${action}?conn_name=${conn_name}&query=${query}&nowPage=${paging.beginPage-1}">prev</a>
									</c:if>
									<c:forEach begin="${paging.beginPage}" end="${paging.endPage}"
										step="1" var="index">
										<c:choose>
											<c:when test="${paging.page==index}">
            ${index}
        </c:when>
											<c:otherwise>
												<a
													href="${action}?conn_name=${conn_name}&query=${query}&nowPage=${index}">${index}</a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${paging.next}">
										<a
											href="${action}?conn_name=${conn_name}&query=${query}&nowPage=${paging.endPage+1}">next</a>
									</c:if>

								</div>
	</c:otherwise>
</c:choose>
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
