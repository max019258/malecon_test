<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.gachidata.maleconDB.*"%>
<%@page import="com.gachidata.db.*"%>
<%@page import="com.gachidata.page.*"%>
<%@page import="com.gachidata.excel.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page import= "org.apache.poi.xssf.usermodel.*"%>
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

	
	String file_path=request.getParameter("file_path"); // 불러오고
	
	System.out.println(file_path);
	
	XSSFWorkbook workBook=ReadExcel.getWorkbook(file_path);// workbook 호출 
	String[][] tableContent=ReadExcel.tableContent(workBook);
	pageContext.setAttribute("tableContent",tableContent);
	
	
%>
<table>
		<c:forEach var="i" items="${tableContent}">
			<tr>
				<c:forEach var="j" items="${i}">
					<td>
						${j}
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
</table>



</body>
</html>
