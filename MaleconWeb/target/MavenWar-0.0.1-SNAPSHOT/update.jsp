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
	<!-- 확인용+ new table 넣기->
<%-- 	<%
	 Enumeration enu = request.getParameterNames();
 
	 String strName;

	 while (enu .hasMoreElements()) {
		  strName= (String) enu .nextElement();
 		  System.out.println(strName + ":");
		  System.out.println(request.getParameter(strName)+"<BR>"); 
 } --%>
%>
<!--  확인용 끝 -->
	

	<% 
		int rowCount=Integer.parseInt(request.getParameter("rowCount"));
		pageContext.setAttribute("rowCount",rowCount);// rowCount를 가져와서 셋팅
		
		int rowcnt=Integer.parseInt(request.getParameter("rowcnt"));
		pageContext.setAttribute("rowcnt",rowcnt);// rowCount를 가져와서 셋팅
		
		int colCount=Integer.parseInt(request.getParameter("colCount"));
		pageContext.setAttribute("colCount",colCount);// colCount를 가져와서 셋팅	
		
		String host=request.getParameter("host");
		pageContext.setAttribute("host",host);// 
		
		String service=request.getParameter("service");
		pageContext.setAttribute("service",service);// 
		
		String port=request.getParameter("port");
		pageContext.setAttribute("port",port);// 
		
		String user=request.getParameter("user");
		pageContext.setAttribute("user",user);// 
		
		String pwd=request.getParameter("pwd");
		pageContext.setAttribute("pwd",pwd);//
		
		String table=request.getParameter("table");
		pageContext.setAttribute("table",table);//
		
		String open=request.getParameter("open");
		pageContext.setAttribute("open",open);//
		
		String conn=request.getParameter("conn");
		pageContext.setAttribute("conn",conn);//
		
		String[] del=request.getParameterValues("del");
		pageContext.setAttribute("del",del);
		
		System.out.println(del+"은 존재한다.");
		
 String[] del_in=request.getParameterValues("del_in");
		pageContext.setAttribute("del_in",del_in);

			%>
			<!-- db 재료 및 connection  만드는 중 -->
	<%
		String conne="";
		DbMaterial dbMaterial=new DbMaterial(conne, host, service, port, user, pwd);
		Connection connection=(Connection)session.getAttribute(conn);
		if (connection==null){
			connection=DbConn.getConnection(dbMaterial);
		}
		
	%>
	<!-- delete문  -->
			<%
			int deleteCount=0;
			if(del!=null){
				for(int i=0;i<del.length;i++){
					String str_i=del[i];
					try{
					DbConn.deleteTable(connection,open,table,request.getParameter("oldArray["+str_i+"][0]"));
					}catch(Exception e){
						out.println("<script> alert('존재하지 않은 rowid를 삭제 하였습니다.(rowId:"+request.getParameter("oldArray["+str_i+"][0]")+")');");
						out.println("</script>");
					}
					deleteCount++;
				}
				
			}
			
			%>
			
		 <!-- newTable 과 OldTable비교후 테이블 업데이트 -->
	<%
		int updateCount=0;
		StaticTable staticTable=new StaticTable();
		String newArray;
		String oldArray;
		for(int i=1;i<rowCount;i++ )
		{
			boolean change=false;
			HashMap<String,String> newMap =new HashMap<String,String>();
			HashMap<String,String> oldMap =new HashMap<String,String>();
			//------------------------------------------------------------- 바뀐여부 확인
			for(int j=1;j<colCount;j++)
			{
				String str_i=Integer.toString(i);
				String str_j=Integer.toString(j);
				newArray=request.getParameter("newArray["+str_i+"]["+str_j+"]");
				oldArray=request.getParameter("oldArray["+str_i+"]["+str_j+"]");
				String colName=request.getParameter("newArray[0]["+str_j+"]");   //컬럼 이름
				String idName=request.getParameter("oldArray["+str_i+"][0]"); 	// rowid
				if(newArray!=null){
					if(!newArray.equals(oldArray)){
						change=true;
						break;
					}
				}
			}
			//--------------------------------
			//-----------------바뀐 행은 업데이트한다.-------------
			if(change==true){
				for(int j=1;j<colCount;j++)
				{
					String str_i=Integer.toString(i);
					String str_j=Integer.toString(j);
					newArray=request.getParameter("newArray["+str_i+"]["+str_j+"]");
					oldArray=request.getParameter("oldArray["+str_i+"]["+str_j+"]");
					String colName=request.getParameter("newArray[0]["+str_j+"]");   //컬럼 이름
					String idName=request.getParameter("oldArray["+str_i+"][0]"); 	// rowid
					
					if (change){
						
						try{			//날짜로 형변환 시도하고
							SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							java.util.Date date_newArray = fm.parse(newArray);
							java.util.Date date_oldArray = fm.parse(oldArray);
							
							newArray="to_date('"+newArray+"','YYYY-MM-DD HH24:MI:SS')";
							oldArray="to_date('"+oldArray+"','YYYY-MM-DD HH24:MI:SS')";
						}catch(Exception e){
							newArray="'"+newArray+"'";
							oldArray="'"+oldArray+"'";
						}	
						
					newMap.put(colName,newArray);
					oldMap.put(colName,oldArray);
					oldMap.put("rowid","'"+idName+"'");
					}
				}
				 try{
					 	int cnt=DbConn.updateTable(connection,open,table,newMap,oldMap);
					 	updateCount+=cnt;
					 	 if(cnt==0){
								out.println("<script> alert('실제 내용과 일치하지 않은 내용을 update하였습니다.');");
								out.println("</script>");							 
						 }
					 }catch(Exception e)
					 {
						out.println("<script> alert('업데이트 오류발생');");
						out.println("</script>");
					 }
				
			
			}
		}
		
		%>
		<!-- INSERT 문 -->
	 <%
	 int added_row=rowcnt-rowCount; //추가된 row
	 int condition_cnt=0; //조건을 위한 cnt
	 
	 int insertCount=0;
	 System.out.println("added_row = "+added_row);
	
	while(added_row!=condition_cnt){
		 if(request.getParameter("newRow["+rowCount+"][1]")!=null){ 
			 	
			 	String sql="";// 안될시를 대비한 sql
			 	ArrayList<String> insert_array = new ArrayList<String>(); //arrayList 초기화
				
				for (int i=1;i<colCount;i++){
					String newRow=request.getParameter("newRow["+rowCount+"]["+i+"]");
					try{			//날짜로 형변환 시도하고
						SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date date_newRow = fm.parse(newRow);					
						
						newRow="to_date('"+newRow+"','YYYY-MM-DD HH24:MI:SS')";						
					}catch(Exception e){
						newRow="'"+newRow+"'";
					}					
					insert_array.add(newRow); // 인서트할 배열 추가
				}
				
				
				try{
					DbConn.insertTable(connection, open, table, insert_array);// 해당 배열을 insert 한다 
				}catch(SQLSyntaxErrorException sqlE){
					out.println("<script> alert('옳지 않은 데이터형식을 넣으셨습니다.');");
					out.println("</script>");
					insertCount--;
				}catch (SQLIntegrityConstraintViolationException SCVE){
					out.println("<script> alert('PK에 null을 넣으셨습니다.');");
					out.println("</script>");
					insertCount--;
				}catch (SQLDataException SQLDE){
					out.println("<script> alert('value larger than specified precision allowed for this column');");
					out.println("</script>");
					insertCount--;
				}
			
				condition_cnt++;
				insertCount++;
				
		}
		 rowCount++;
	}
	 
	 %>
	 
	 
	 <%
	 //connection.commit();
	 session.setAttribute("conn",connection);
	 
	 	out.println("<script>alert('"+insertCount+"개의 행이 삽입되고"+updateCount+"개의 행이 바뀌었으며"+deleteCount+"개의 행이 삭제되었습니다.');");
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