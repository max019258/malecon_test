package com.gachidata.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.hamcrest.core.SubstringMatcher;





public class DbConn
{
	
       public  static Connection getConnection(DbMaterial dbmaterial)
       {
           Connection conn = null;
           String url = null;
           try {
               String user = dbmaterial.getUserName(); 
               String pw = dbmaterial.getPassword();
               url = "jdbc:oracle:thin:@"+dbmaterial.getHostName()+":"+dbmaterial.getPortName()+":"+
            		   																dbmaterial.getServiceName(); //XE =SID
               
               Class.forName("oracle.jdbc.driver.OracleDriver");  //jdbc드라이버 로딩      
               conn = DriverManager.getConnection(url, user, pw);
               
           } catch (ClassNotFoundException cnfe) {
               System.out.println("User DB 드라이버 로딩 실패 :"+url);
           } catch (SQLException sqle) {
               System.out.println("User DB 접속실패 : "+url);
           } catch (Exception e) {
               System.out.println("Unkonwn error"+url);
               e.printStackTrace();
           }
           return conn;     
       }
       
		  public static ArrayList<String> getOpen(Connection connection ) throws SQLException {  //OpenList들 반환
			  String sql="  select owner from all_tables " + 
			  		" where owner " + 
			  		" in " + 
			  		" (SELECT username FROM DBA_USERS where account_status='OPEN'and username!='SYS'and username!='SYSTEM') group by owner order by owner";
			  System.out.println(sql);
			
			  ArrayList<String> openList= new ArrayList<String>(); //어레이 리스트 선언 
			  try {
					  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
					  ResultSet rs=pstmt.executeQuery();  /// 연결 22			 
					
					  while (rs.next())  //DB접근 후 출력
					  { 
						  openList.add(rs.getString(1));// 어레이리스트로 올리기
						
					  }	 
			  }	catch(SQLSyntaxErrorException e)   // 만약에 오픈 테이블을 못볼때 예외처리
			  {
				
			  }
			return openList;  //openList 반환		  
		 }
		  
		  public static ArrayList<String> getTable(Connection connection,String owner) throws SQLException {  //OpenList들 반환
			  String sql="  select table_name from all_tables " + 
			  		" where owner " + 
			  		" in " + 
			  		" (SELECT username FROM DBA_USERS where account_status='OPEN'and username!='SYS'and username!='SYSTEM') "+
			  		"and owner='"+owner+"' order by table_name";
			  System.out.println(sql);
			
			  ArrayList<String> ownerList= new ArrayList<String>(); //어레이 리스트 선언 
			  try {
					  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
					  ResultSet rs=pstmt.executeQuery();  /// 연결 22			 
					
					  while (rs.next())  //DB접근 후 출력
					  { 
						  ownerList.add(rs.getString(1));// 어레이리스트로 올리기
					  }	 
			  }	catch(SQLSyntaxErrorException e)   // 만약에 오픈 테이블을 못볼때 예외처리
			  {
				  sql="select table_name from all_tables where owner='"+owner+"'";
				  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
				  ResultSet rs=pstmt.executeQuery();  /// 연결 22			 
				  
				  while (rs.next())  //DB접근 후 출력
				  { 
					  ownerList.add(rs.getString(1));// 어레이리스트로 올리기
				  }	 
			  }
			return ownerList;  //openList 반환		  
		 }
		  public static String getPK(Connection connection,String owner,String table ) throws SQLException {  //테이블 전체내용 반환
			
			  String sql="  SELECT B.COLUMN_NAME " + 
			  		"        FROM ALL_CONSTRAINTS A, ALL_CONS_COLUMNS B" + 
			  		"        WHERE A.CONSTRAINT_NAME = B.CONSTRAINT_NAME" + 
			  		"        AND A.OWNER='"+owner+"' " + 
			  		"        AND A.TABLE_NAME = '"+table+"' " + 
			  		"        AND CONSTRAINT_TYPE='P'";
			  System.out.println(sql);
			
			 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22		
			  String PK = null;
			  while (rs.next())  //DB접근 후 출력
			  { 
					PK=rs.getString(1);//배열에 먼저 넣고 
			  }
			  System.out.println("PK값은 : "+PK);
			  if (PK ==null)
			  {
				  PK="ROWID";
			  }
			 
			  return PK;
			}
		  
		  
		  public static int getTotal(Connection connection,String owner,String table ) throws SQLException {  // 테이블 개수
		
			  String sql="  select count(1) from "+owner+"."+table;
			  
			  System.out.println(sql);
			
			  int rowCount=0;
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22		 
				
			  while (rs.next())  //DB접근 후 출력
			  { 			 
				  rowCount=rs.getInt(1);
			  }
			  System.out.println("rowCount="+rowCount);
			return rowCount;  //openList 반환		  
		 }
		  public static int getTotal(Connection connection,String owner,String table ,String where) throws SQLException {  // 테이블 개수
				
			  String sql="  select count(1) from "+owner+"."+table+" where "+ where;
			  
			  System.out.println(sql);
			
			  int rowCount=0;
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22		 
				
			  while (rs.next())  //DB접근 후 출력
			  { 			 
				  rowCount=rs.getInt(1);
			  }
			  System.out.println("rowCount="+rowCount);
			return rowCount;  //openList 반환		  
		 }

		 
		  public static ArrayList<String[]> getAll(Connection connection,String owner,String table,int beginRow,int endRow ) throws SQLException {  //테이블 1~n까지 반화
			  String PK= getPK(connection,owner,table);
			  String sql="select *" + 
			  		"from(" + 
			  		"select *" + 
			  		"    from" + 
			  		"    (" + 
			  		"        select rownum row_num,rowid row_id,a.*" + 
			  		"        from "+owner+"."+table+" a" + 
			  		"        where "+PK+" is not null" + 
			  		"        order by "+PK + 
			  		"    )" + 
			  		"    where row_num>="+beginRow + 
			  		"    )" + 
			  		"where rownum<="+(endRow-beginRow+1);
			  
			
			  System.out.println(sql);
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //어레이 리스트 선언 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//컬럼 갯수 얻어오기

		        System.out.println("column count : " + rsSize);

		        String[] array_column = new String[rsSize-1]; //컬럼명 배열
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//컬럼명 넣기
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//배열에 먼저 넣고 
		        
		        }
		        openList.add(array_column);//컬럼명 넣기완료
			  

			  System.out.println("row개수: "+rsSize);
		
			  while (rs.next())  //DB접근 후 출력
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//배열에 먼저 넣고 
					  
				  }
				  openList.add(array);// 어레이리스트로 올리기

			  }

			return openList;  //openList 반환	  
		 }
		  
		  public static ArrayList<String[]> getAllExcel(Connection connection,String owner,String table) throws SQLException {  //테이블 1~n까지 반화
			  String PK= getPK(connection,owner,table);
			  String sql= 
			  		"select *" + 
			  		"    from" + 
			  		"    (" + 
			  		"        select rownum row_num,rowid row_id,a.*" + 
			  		"        from "+owner+"."+table+" a" + 
			  		"        where "+PK+" is not null" + 
			  		"        order by "+PK + 
			  		"    )" ;
			 
			  
			
			  System.out.println(sql);
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //어레이 리스트 선언 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//컬럼 갯수 얻어오기

		        System.out.println("column count : " + rsSize);

		        String[] array_column = new String[rsSize-1]; //컬럼명 배열
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//컬럼명 넣기
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//배열에 먼저 넣고 
		        
		        }
		        openList.add(array_column);//컬럼명 넣기완료
			  

			  System.out.println("row개수: "+rsSize);
		
			  while (rs.next())  //DB접근 후 출력
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//배열에 먼저 넣고 
					  
				  }
				  openList.add(array);// 어레이리스트로 올리기

			  }

			return openList;  //openList 반환	  
		 }
		  
		  
		  public static ArrayList<String[]> getAllExcel(Connection connection,String owner,String table,String where) throws SQLException {  //테이블 1~n까지 반화
			  String PK= getPK(connection,owner,table);
			  String Where=where.trim();
			  String sql=
			  		"select *" + 
			  		"    from" + 
			  		"    (" + 
			  		"        select rownum row_num,rowid row_id,a.*" + 
			  		"        from "+owner+"."+table+" a" + 
			  		"        where "+PK+" is not null and "+Where  + 
			  		"        order by "+PK + 
			  		"    )";
			  	
			
			  System.out.println(sql);
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //어레이 리스트 선언 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//컬럼 갯수 얻어오기

		        System.out.println("column count : " + rsSize);
		        
		      

		        String[] array_column = new String[rsSize-1]; //컬럼명 배열
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//컬럼명 넣기
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//배열에 먼저 넣고 
		        
		        }
		        openList.add(array_column);//컬럼명 넣기완료
			  

		        
			  System.out.println("row개수: "+rsSize);
		
			  while (rs.next())  //DB접근 후 출력
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//배열에 먼저 넣고 
					  
				  }
				  openList.add(array);// 어레이리스트로 올리기

			  }

			return openList;  //openList 반환	  
		 }
		 
		 
		  public static ArrayList<String[]> getAll(Connection connection,String owner,String table,int beginRow,int endRow ,String where) throws SQLException {  //테이블 1~n까지 반화
			  String PK= getPK(connection,owner,table);
			  String Where=where.trim();
			  String sql="select *" + 
			  		"from(" + 
			  		"select *" + 
			  		"    from" + 
			  		"    (" + 
			  		"        select rownum row_num,rowid row_id,a.*" + 
			  		"        from "+owner+"."+table+" a" + 
			  		"        where "+PK+" is not null and "+Where  + 
			  		"        order by "+PK + 
			  		"    )" + 
			  		"    where row_num>="+beginRow + 
			  		"    )" + 
			  		"where rownum<="+(endRow-beginRow+1);
			  
			
			  System.out.println(sql);
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //어레이 리스트 선언 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  ResultSet rs=pstmt.executeQuery();  /// 연결 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//컬럼 갯수 얻어오기

		        System.out.println("column count : " + rsSize);
		        
		      

		        String[] array_column = new String[rsSize-1]; //컬럼명 배열
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//컬럼명 넣기
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//배열에 먼저 넣고 
		        
		        }
		        openList.add(array_column);//컬럼명 넣기완료
			  

		        
			  System.out.println("row개수: "+rsSize);
		
			  while (rs.next())  //DB접근 후 출력
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//배열에 먼저 넣고 
					  
				  }
				  openList.add(array);// 어레이리스트로 올리기

			  }

			return openList;  //openList 반환	  
		 }
		  
		  public static int updateTable(Connection connection,String owner,String table,HashMap<String,String> newMap,HashMap<String,String> oldMap ) throws SQLException
		  {
			  connection.setAutoCommit(false);
			  String newStr="";
			  for(String key:newMap.keySet()) {
				  newStr+=key+" = "+newMap.get(key)+" ,";
			  }
			  newStr=newStr.substring(0,newStr.length()-1);//끝에있는 , 뺴기
			  //--------------------------------------------------------------------
			  String oldStr="";
			  for(String key:oldMap.keySet()) {
				  oldStr+=key+" = "+oldMap.get(key)+" and ";
			  }
			  oldStr=oldStr.substring(0,oldStr.length()-4);//끝에있는 and 뺴기
			  oldStr=oldStr.replaceAll("= ''","is null"); //비어 있는 것으 is null로 바뀌기
			  //------------------------------------------------------------------------
			  
			  String sql="update "+owner+"."+table+" set "+newStr+" where "+oldStr;
			  
			  System.out.println("update:"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  
			  int r=pstmt.executeUpdate(); //변경된 로우값
			  connection.commit();
			  
			  return r;
		  }
		  
		  public static int deleteTable(Connection connection,String owner,String table,String rowid) throws SQLException
		  {
			  connection.setAutoCommit(false);
			  String sql="delete from "+owner+"."+table+" where rowid='"+rowid+"'";
			  
			  System.out.println("delete:"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  
			  int r=pstmt.executeUpdate(); //변경된 로우값
			
			  connection.commit();
			  return r;
		  }
		  
		  public static String insertTable(Connection connection,String owner,String table,ArrayList<String> arr) throws SQLException
		  {
			  connection.setAutoCommit(false);
			  String value="";
			  for(String i:arr) {
				  value+=i+",";
			  }
			  value=value.substring(0,value.length()-1); //맨끝에 있는 애 빼기
			  
			  String sql="insert into "+owner+"."+table+" values ("+value+")";
			  
			  System.out.println("insert :"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // 연결
			  
			  pstmt.executeUpdate();
			  connection.commit();
			
			  return sql;
		  }
       
}

