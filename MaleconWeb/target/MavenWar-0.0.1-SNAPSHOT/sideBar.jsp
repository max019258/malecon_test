<%@ page import="com.gachidata.maleconDB.*"%>
<%@ page import="com.gachidata.db.*"%>
<%@ page import="java.util.*"%>
<%@page import="java.sql.Connection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang=''>
<head>

   <meta charset='utf-8'>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="assets/css/styles.css">

   <title>CSS MenuMaker</title>
</head>
<body>
<%
 	ArrayList<String> connName=MaleconDb.selectConn(); //conn이름 불러오기
	pageContext.setAttribute("connName", connName);// 연결 이름 셋팅
	
	 //사이드바 새로고침 여부
	if(session.getAttribute("reload_sideBar")!=null){
		session.setAttribute("reload_sideBar",true); // 사이드바 새로고침은 트루
	}
	
	HashMap<String,HashMap<String,ArrayList<String>>> sideBar =new HashMap<>();
%>

<div id='cssmenu'>
 <a href="reload_sideBar.jsp"><img src="images/logo.png" width="90%" height="100"/></a>
<div style="margin-bottom:10px;margin-top:10px;"><input type="text" id="keyword" style="color:black;"/></div>

<ul>

	<!-- <li style="background-color:#f8f8f8;"><a href='reload_sideBar.jsp'>reload sideBar</a></li> -->
   <li style="background-color:#f8f8f8;"><a href='newConnect.jsp'>new Connect</a></li>
  
   <%if(session.getAttribute("reload_sideBar")==null) {%>
   <c:forEach var="i" items="${connName}">
   <li class='active has-sub'><a href='#'>${i}</a>
      <ul>
      	<% 
      		String i=(String)pageContext.getAttribute("i"); //jstl변수 jsp에서 사용
      		
      		ArrayList<String> material=MaleconDb.selectAll(i);// DB재료들 가지고온다.
      		
      		DbMaterial dbMaterialS=new DbMaterial(material.get(0),material.get(1),material.get(2),material.get(3),material.get(4),material.get(5));// Db 재료들
      		
      		
      		Connection connectionS = DbConn.getConnection(dbMaterialS); //Dbconnection 이다	
      		
      		if(session.getAttribute(i)==null){
      			session.setAttribute(i,connectionS); // 세션 변수 선언
      		}
      		
      		ArrayList<String> openName=DbConn.getOpen(connectionS); // [오너이름] 가져오기
      		if(openName.size()==0){
      			openName.add(material.get(4).toUpperCase());
      		}
			pageContext.setAttribute("openName", openName);// jsp변수 jstl에서 사용
			
			
			HashMap<String,ArrayList<String>> sideBar2 =new HashMap<>();
		%>
		<c:forEach var ="j" items="${openName}">
         <li class='has-sub'><a href='#'>${j}</a>
            <ul>
             	<% 
      		String j=(String)pageContext.getAttribute("j"); //jstl변수 jsp에서 사용
      		
      		ArrayList<String> tableName=DbConn.getTable(connectionS, j);
      		
      		
			pageContext.setAttribute("tableName", tableName);// 오픈 오너 이름 셋팅
			
			ArrayList<String> sideBar3 = new ArrayList<>();
		%>
			<c:forEach var ="k" items="${tableName}">
               <li><a href='showTable.jsp?i=${i}&j=${j}&k=${k}'>${k}</a></li> <!-- 테이블 보기 -->  
                <% 
                String k=(String)pageContext.getAttribute("k");
                sideBar3.add(k);%>            
               </c:forEach>
            </ul>
         </li>
         	 <% sideBar2.put(j,tableName);%>
         </c:forEach>
         
      </ul>
   </li>
  <% 	sideBar.put(i,sideBar2);%> 
 </c:forEach>
 <%
 	session.setAttribute("store_sideBar",sideBar);
 session.setAttribute("reload_sideBar",true);
   }
   else{%>
  <%
  Object store_sideBar=session.getAttribute("store_sideBar");
  pageContext.setAttribute("store_sideBar",store_sideBar);
 %>
  <c:forEach var="i" items="${store_sideBar.keySet()}">
 	<li class='active has-sub'><a href='#' class='b'>${i} </a>
	 	<ul>
	 		<c:forEach var ="j" items="${store_sideBar.get(i).keySet()}">
         <li class='has-sub'><a href='#' class='b'>${j} </a>
         <ul>
         		<c:forEach var ="k" items="${store_sideBar.get(i).get(j)}">
         		  <li><a href='showTable.jsp?i=${i}&j=${j}&k=${k}'>${k} </a></li> <!-- 테이블 보기 -->  
         		</c:forEach>
         	</ul>	
         	</li>
         	</c:forEach>
	 	</ul>
 	</li>
 	</c:forEach>
 
 <% }%>
</ul>
</div>

</body>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
   <script src="assets/js/script.js"></script>
<html>