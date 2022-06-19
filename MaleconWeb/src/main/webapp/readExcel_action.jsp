<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.gachidata.maleconDB.*"%>
<%@page import="com.gachidata.db.*"%>
<%@page import="com.gachidata.page.*"%>
<%@page import="java.sql.*"%>
<%@page import= "org.apache.poi.xssf.usermodel.*"%>
<%@page import= "org.apache.poi.ooxml.*"%>
<%@page import="com.gachidata.excel.*"%>
<%@page import=" org.apache.poi.openxml4j.exceptions.*"%>
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


	<!-- Wrapper -->
	<div id="wrapper">


		<div id="main">
			<div class='inner' id='div_reload'>
				<section>
			
					<%
					try{
							String file_path=request.getParameter("file_path"); // path 받아오기
						
							//------------ ------------------------
							 XSSFWorkbook workBook=ReadExcel.getWorkbook(file_path);//worbook 받아오기
	
							
							Map<String,String> sheetName=ReadExcel.sheetName(workBook);// conn,owner,table MAp으로 갖고옴
							String[][] tableContent= ReadExcel.tableContent(workBook); // tableContent 갖고온다.
						
							System.out.println(sheetName.get("conn"));
							System.out.println(sheetName.get("owner"));
							System.out.println(sheetName.get("table"));
							
							ArrayList<String> material=MaleconDb.selectAll(sheetName.get("conn"));
							DbMaterial dbMaterials=new DbMaterial(material.get(0),material.get(1),material.get(2),material.get(3),material.get(4),material.get(5));// Db 재료들
							
							Connection connection_excel=DbConn.getConnection(dbMaterials);// 커넥션 가지고옴 
							
							
							
							//----------------------------------------------------------------
							try{
								DbConn.deleteAll(connection_excel,sheetName.get("owner"),sheetName.get("table")); //전체 삭제
								
								for(int i=1;i<tableContent.length;i++){
									DbConn.insert_columnName(connection_excel,sheetName.get("owner"),sheetName.get("table"),tableContent[0],tableContent[i]);
								}
							}catch(SQLIntegrityConstraintViolationException e){
								out.println("<script>alert('PK가 중복되었거나 NULL값입니다. PK수정후 다시 시도해주세요');");
								out.println(  "window.location = 'readExcel.jsp'"  ); 
								out.println("</script>");
							}						
							
							out.println("<script>alert('적용되었습니다.');");
							out.println(  "window.location = 'showTable.jsp?i="+sheetName.get("conn")+"&j="+sheetName.get("owner")+"&k="+sheetName.get("table")+"'"  ); 
							out.println("</script>");
				 	 }
					 catch (ArrayIndexOutOfBoundsException e){
							out.println("<script>alert('없는 컬럼을 넣으셨습니다. 기존에 없던 컬럼을 삭제해 주세요');");
							out.println(  "window.location = 'readExcel.jsp'"  ); 
							out.println("</script>");
					}catch (SQLDataException|SQLSyntaxErrorException e2) {
							out.println("<script>alert('데이터 형식이 맞지 않습니다. 데이터 형식을 잘 확인해주세요');");
							out.println(  "window.location = 'readExcel.jsp'"  ); 
							out.println("</script>");
					}catch (POIXMLException e3){
							out.println("<script>alert('엑셀파일이 아닙니다. 엑셀 파일을 넣어주세요');");
							out.println(  "window.location = 'readExcel.jsp'"  ); 
							out.println("</script>");
					}/* catch(Exception e4){
							out.println("<script>alert('시트이름을 바꾸신 것 같아요 시트 이름을 커넥션이름_오너이름_테이블이름 으로 지정해주세요');");
							out.println(  "window.location = 'readExcel.jsp'"  ); 
							out.println("</script>");
					} */
					 
					
					%>

				
				
				</section>

			</div>
		</div>
	
	</div>



</body>
</html>
