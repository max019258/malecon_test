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
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="assets/js/script.js"></script>
</head>

<body class="is-preload">

<% 
String conn_name=request.getParameter("conn_name");
String query=request.getParameter("query");

pageContext.setAttribute("conn_name",conn_name);
pageContext.setAttribute("query",query);

System.out.println("입력 받은 것 :"+query);
// 만약에 쿼리 :앞글자가 특수문자가 아니라면 
  

  ArrayList<String> material=MaleconDb.selectAll(conn_name);
  DbMaterial dbMaterials=new DbMaterial(material.get(0),material.get(1),material.get(2),material.get(3),material.get(4),material.get(5));// Db 재료들
  
  HashSet<String> bind= new HashSet<String>();// 바인드 변수 넣어 놓는 곳
  
    String[] arr= query.split(":"); // :을 기준으로 스플릿
    
    int count=0;
    for(String i : arr){
      count++; //count 세서 if의 용도
      if(count!=1){ // count가 맨 처음것이 아니라면
        String[] arr2=i.trim().split(" "); // 양쪽 공백 제거하고 띄어쓰기를 스플릿
        
        bind.add(arr2[0]); // 바인드에 올리기, 바인드에 올린 것을
      }
    }
    // df
	    
    

  pageContext.setAttribute("bind",bind);
  pageContext.setAttribute("conn_name",conn_name);
  pageContext.setAttribute("query",query);
%>

	<table style="width:100%">
			<tr>
				<th>
					Variable
				</th>
				<th>
					Value
				</th>
				<th>
					Type
				</th>
			</tr>
			<% int cnt=0;//정수형 변수   %>
			<c:forEach var="i" items="${bind}">
			<%cnt++; %>
			<%pageContext.setAttribute("cnt",cnt); %>
				<tr>
					<td>
						<input type="text"  value="${i}" name="var" disabled>
					</td>
					<td>
					 	<input type="text" name="value" >
					</td>
					<td>
						<select id="type" name="type" size="1">
									<option value="">선택하세요.</option>						
									<option value="String">String</option>
									<option value="Number">Number</option>
									<option value="Date">Date</option>
									<option value="Char">Char</option>
									<option value="National String">National String</option>
									<option value="NCHAR">NCHAR</option>
													
								</select>
					</td>
				</tr>
			</c:forEach>
	</table>
	<input type='button' value='complete'  onclick='viewBind()' >
	
	


</body>
</html>