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

	
		request.setCharacterEncoding("UTF-8");
	String conn = request.getParameter("i");
	String open = request.getParameter("j");
	String table = request.getParameter("k");

	pageContext.setAttribute("conn", conn);
	pageContext.setAttribute("open", open);
	pageContext.setAttribute("table", table);
	int nowPage = 1;
	try {
		nowPage = Integer.parseInt(request.getParameter("p")); //받아온 값 가지고 오기 
	} catch (NumberFormatException e) {
		nowPage = 1;
	}
	Integer nowPage_temp = nowPage;

	String where = null;
	try {
		where = request.getParameter("w");
		if( where.equals(""))
		{
			where=null;
		}
	} catch (Exception e) {
		where = null;
	}
	pageContext.setAttribute("where",where);
		
	
	%>
	<%
		//페이지 설정
	Paging paging = new Paging();
	paging.setPage(nowPage_temp);
	%>

	<%
		ArrayList<String> userINF = MaleconDb.selectAll(conn); // 출력할 정보 가지고오기

	String host = userINF.get(1);
	String service = userINF.get(2);
	String port = userINF.get(3);
	String user = userINF.get(4);
	String pwd = userINF.get(5);
	
	pageContext.setAttribute("host",host);
	pageContext.setAttribute("service",service);
	pageContext.setAttribute("port",port);
	pageContext.setAttribute("user",user);
	pageContext.setAttribute("pwd",pwd);

	/* Connection connection=(Connection)request.getAttribute("connection_commit");
	if (connection==null){
	DbMaterial userMaterial = new DbMaterial(conn, host, service, port, user, pwd);//userDB재료들
		connection = DbConn.getConnection(userMaterial);
	} */
	//Connection connection=(Connection)session.getAttribute(conn);
	
	Connection connection=(Connection)session.getAttribute("conn");
	if (connection==null){
	DbMaterial userMaterial = new DbMaterial(conn, host, service, port, user, pwd);//userDB재료들
		connection = DbConn.getConnection(userMaterial);
	}
	session.setAttribute("conn_name",conn);
	session.setAttribute("open_name",open); 
	session.setAttribute("table_name",table);
	
	int total;
	if (where == null) {
		total = DbConn.getTotal(connection, open, table); //페이지 설정 시작
	} else {
		total = DbConn.getTotal(connection, open, table, where);
	}

	paging.setTotalCount(total);
	paging.paging();// 변수 할당

	pageContext.setAttribute("paging", paging);
	pageContext.setAttribute("total", total);//페이지 설정 끝

	ArrayList<String[]> all;
	if (where == null) {
		all = DbConn.getAll(connection, open, table, paging.getBeginRow(), paging.getEndRow());//1~n까지의 테이블
	} else {
		all = DbConn.getAll(connection, open, table, paging.getBeginRow(), paging.getEndRow(), where);//1~n까지의 테이블
	}
	
	ArrayList<String[]> oldTable=all;
	pageContext.setAttribute("oldTable",oldTable);
	
	
	int rowCount=all.size();
	int colCount=(all.get(0)).length;

	

	pageContext.setAttribute("rowCount",rowCount); //rowCount 셋팅
	pageContext.setAttribute("colCount",colCount); //columnCount 셋팅
	
	pageContext.setAttribute("all", all); // 테이블 목록
	%>




	<!-- Wrapper -->
	<div id="wrapper">


		<div id="main">
			<div class='inner' id='div_reload'>
				<section>
					<div><b> 1. 연결 이름 : ${conn} </b>&nbsp; <b> 2. 사용자 이름 : ${open} </b> 
					&nbsp; <b> 3. 테이블 이름 : ${table} </b>
					
					<div>
					<div style="height:70px">
					<div style="float: left; height:100% width:70%">
						<form name="search" align="left" method="get"
							action="showTable.jsp?i=${conn}&j=${open}&k=${table}"
							onsubmit="return keyword_check()">
		

							<input type="hidden" name="i" value="${conn}"> <input
								type="hidden" name="j" value="${open}"> <input
								type="hidden" name="k" value="${table}">
							<!-- align : 정렬 , style : 스타일 정보 포함 (margin : 여백 설정) , method : 전달 방식 ,  action : submit 시 이동 경로 ,onsubmit : submit 클릭시 호출 조건 (true 일 때 action으로 넘어감 )-->
							<table style="border:hidden">
								<tr>
									<td><input type="text" name="w" ></td>
										
									<td style="width:50px"><input type="submit" value="search"></td>
								</tr>
							</table>
						</form>

					</div>



					<%
						ArrayList<String> nowListIn; //안에 있는 nowList
					ArrayList<ArrayList<String>> nowListOut;
					%>
					<div style="float: left; padding-top:30px; padding-left:20px;height:50px; width:50px"> <!-- 엑셀 버튼 -->
					 <a href="exel"><img src="images/exel.png" width="20px" height="20px"/></a>
					</div>

					
					<div style="float: left; height:100%; width:10%; text-align:right; padding-top:30px">건수 : ${total}</div>
					</div>
					
					<!-- -----------------------------실질 테이블 내용---------------------------------------------------------- -->
				
						
							<div id="div_table" style="width: 100%; height: 550px; overflow: auto;position:relative">
								<form name="update" method="post" action="update.jsp">
							<div id="div_insert"></div>
					
					<input type="hidden" value="${conn}" name="conn">
					<input type="hidden" value="${open}" name="open">
					<input type="hidden" value="${table}" name="table">
					<input type="hidden" value="${rowCount}" name="rowCount">
					<input type="hidden" value="${colCount}" name="colCount">
					
					<input type="hidden" value="${host}" name="host">
					<input type="hidden" value="${service}" name="service">
					<input type="hidden" value="${port}" name="port">
					<input type="hidden" value="${user}" name="user">
					<input type="hidden" value="${pwd}" name="pwd">
					
					
					<%int count=0; %>
					<c:forEach var="q" items="${oldTable}">   <!-- column이름 -->
					
					<%
						int count_2=0;
					%>
							<c:forEach var="w" items="${q}">
							<%pageContext.setAttribute("count",count);
							pageContext.setAttribute("count_2",count_2); %> 
								<input type="hidden" value="${w}" name="oldArray[${count}][${count_2}]">
								<% count_2++; %>
								</c:forEach>
								<% count++; %>
						</c:forEach>
						
										
								<table id='table' style="border-color:black" >
								<thead style="overflow: auto; width:inherit">
									<tr >
									<%int count0 = 0; 
									%>
									<th nowrap style="width:50px"><input type="checkbox" name="all"  onclick="setAll(this)" ></th>
										<c:forEach var="t" items="${all.get(0)}">   <!-- column이름 -->
										<%
										pageContext.setAttribute("count0",count0);%>
											
										<%if(count0!=0) {%>
											<th nowrap>${t}</th>
											<%} %>
												<input type="hidden" value="${t}"name="newArray[0][${count0}]">
											<% count0++ ;%>
										</c:forEach>
									</tr>
									</thead>

									<%
										int count1 = 1;
								

									for (int i = 1; i <= all.size() - 1; i++) {
										String[] array;
										array = all.get(i);
										pageContext.setAttribute("i", i);
									%>
									<tr id="row${i}" class="row_content" >
										<%
											pageContext.setAttribute("array", array);
										int count2 = 0;
										
										%> 
										
										
										<td>
			<input type="checkbox" name="del" value="${i}" onclick="setBg()" >
										
										</td>

										<c:forEach var="j" items="${array}">
										<%
													
												String k = (String) pageContext.getAttribute("j");
												pageContext.setAttribute("k", k);

												pageContext.setAttribute("count1", count1);
												pageContext.setAttribute("count2", count2);
												
												%> 
										<%if (count2==0){%>
										<input  id="newArray[${count1}][${count2}]" 	type="hidden" value="${k}"	name="newArray[${count1}][${count2}]" style="border:none;width:${k.length()+20}mm">
										<%}else{%>
											<td nowrap onmouseover="mouse_on(this)" onmouseout="mouse_off(this)" onclick="mouse_click(this)">
												
												
		<input  id="newArray[${count1}][${count2}]" type="text" value="${k}"	name="newArray[${count1}][${count2}]" style="border:none; width:${k.length()+4}em" disabled>
												<%} count2++;%>
											</td>
										</c:forEach>
									</tr>
									<%
									count1++;
										}
									%>
								</table>
								<%
									String url = request.getRequestURL().toString(); //url 가져오기
								System.out.println(url);
								pageContext.setAttribute("url", url);
								%>
								

							</div>

							<!-- -----------------------------실질 테이블 내용 끝---------------------------------------------------------- -->
							<div>
								<div style="float: left; width: 10%; padding-top: 2%" >
									<input type="submit" onclick='commit()' value="commit" style="width:95%">														
									</div>
							<!-- 	<div style="float: left; width: 10%; padding-top: 2%" >
									<input type="button" onclick='goCommit()' value="commit" style="width:95%">																	
									</div> -->
									<div style="float: left; width: 10%; padding-top: 2%">
									<input type='button' value='rollback'   onclick="goRollback()" style="width:95%">
									</div>
								
									</form>	
							<!------------------------- 페이지 ----------------------------------------->
						<!-- 		<div
									style="float: left; width: 70%; padding-right: 20%; padding-left: 20%; padding-top: 2%"> -->
									<div style="float:left; width: 40% ;padding-top: 2% ;text-align:center">

									<c:url var="action" value="${url}"  />
									<c:if test="${paging.prev}">
										<a
											href="${action}?i=${conn}&j=${open}&k=${table}&p=${paging.beginPage-1}&w=${where}">prev</a>
									</c:if>
									<c:forEach begin="${paging.beginPage}" end="${paging.endPage}"
										step="1" var="index">
										<c:choose>
											<c:when test="${paging.page==index}">
            ${index}
        </c:when>
											<c:otherwise>
												<a
													href="${action}?i=${conn}&j=${open}&k=${table}&p=${index}&w=${where}">${index}</a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${paging.next}">
										<a
											href="${action}?i=${conn}&j=${open}&k=${table}&p=${paging.endPage+1}&w=${where}">next</a>
									</c:if>

								</div>
								<!------------------------- 페이지 끝----------------------------------------->
									<div style="float: left; width: 10%; padding-top: 2%; ">
									<input type='button' value='insert'   onclick='addRow()' style="width:95%">
									</div>
									<div style="float: left; width: 10%; padding-top: 2%">
									<input type='button' value='delete'   onclick='deleteRow()' style="width:95%">
									</div>
									<div style="float: left; width: 10%; padding-top: 2%">
									<input type='button' value='update'   onclick='updateRow()' style="width:95%">
									</div>
									
								


							</div>
						</div>
					
				</section>

			</div>
		</div>
		<div id="sidebar">
			<%@include file="sideBar.jsp"%>
		</div>
	</div>


<style>
.changeColor{
	background-color:#bff0ff;
}


</style>

<script>
/* window.onbeforeunload = function() {
	return "떠나려고 하시나요? ㅠㅠ 떠나려고 하시나요? ㅠㅠ 떠나려고 하시나요? ㅠㅠ 떠나려고 하시나요? ㅠㅠ 떠나려고 하시나요? ㅠㅠ 떠나려고 하시나요? ㅠㅠ";
} */
</script>

</body>
</html>
