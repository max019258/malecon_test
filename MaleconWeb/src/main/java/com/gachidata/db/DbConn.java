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
               
               Class.forName("oracle.jdbc.driver.OracleDriver");  //jdbc����̹� �ε�      
               conn = DriverManager.getConnection(url, user, pw);
               
           } catch (ClassNotFoundException cnfe) {
               System.out.println("User DB ����̹� �ε� ���� :"+url);
           } catch (SQLException sqle) {
               System.out.println("User DB ���ӽ��� : "+url);
           } catch (Exception e) {
               System.out.println("Unkonwn error"+url);
               e.printStackTrace();
           }
           return conn;     
       }
       
		  public static ArrayList<String> getOpen(Connection connection ) throws SQLException {  //OpenList�� ��ȯ
			  String sql="  select owner from all_tables " + 
			  		" where owner " + 
			  		" in " + 
			  		" (SELECT username FROM DBA_USERS where account_status='OPEN'and username!='SYS'and username!='SYSTEM') group by owner order by owner";
			  System.out.println(sql);
			
			  ArrayList<String> openList= new ArrayList<String>(); //��� ����Ʈ ���� 
			  try {
					  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
					  ResultSet rs=pstmt.executeQuery();  /// ���� 22			 
					
					  while (rs.next())  //DB���� �� ���
					  { 
						  openList.add(rs.getString(1));// ��̸���Ʈ�� �ø���
						
					  }	 
			  }	catch(SQLSyntaxErrorException e)   // ���࿡ ���� ���̺��� ������ ����ó��
			  {
				
			  }
			return openList;  //openList ��ȯ		  
		 }
		  
		  public static ArrayList<String> getTable(Connection connection,String owner) throws SQLException {  //OpenList�� ��ȯ
			  String sql="  select table_name from all_tables " + 
			  		" where owner " + 
			  		" in " + 
			  		" (SELECT username FROM DBA_USERS where account_status='OPEN'and username!='SYS'and username!='SYSTEM') "+
			  		"and owner='"+owner+"' order by table_name";
			  System.out.println(sql);
			
			  ArrayList<String> ownerList= new ArrayList<String>(); //��� ����Ʈ ���� 
			  try {
					  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
					  ResultSet rs=pstmt.executeQuery();  /// ���� 22			 
					
					  while (rs.next())  //DB���� �� ���
					  { 
						  ownerList.add(rs.getString(1));// ��̸���Ʈ�� �ø���
					  }	 
			  }	catch(SQLSyntaxErrorException e)   // ���࿡ ���� ���̺��� ������ ����ó��
			  {
				  sql="select table_name from all_tables where owner='"+owner+"'";
				  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
				  ResultSet rs=pstmt.executeQuery();  /// ���� 22			 
				  
				  while (rs.next())  //DB���� �� ���
				  { 
					  ownerList.add(rs.getString(1));// ��̸���Ʈ�� �ø���
				  }	 
			  }
			return ownerList;  //openList ��ȯ		  
		 }
		  public static String getPK(Connection connection,String owner,String table ) throws SQLException {  //���̺� ��ü���� ��ȯ
			
			  String sql="  SELECT B.COLUMN_NAME " + 
			  		"        FROM ALL_CONSTRAINTS A, ALL_CONS_COLUMNS B" + 
			  		"        WHERE A.CONSTRAINT_NAME = B.CONSTRAINT_NAME" + 
			  		"        AND A.OWNER='"+owner+"' " + 
			  		"        AND A.TABLE_NAME = '"+table+"' " + 
			  		"        AND CONSTRAINT_TYPE='P'";
			  System.out.println(sql);
			
			 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22		
			  String PK = null;
			  while (rs.next())  //DB���� �� ���
			  { 
					PK=rs.getString(1);//�迭�� ���� �ְ� 
			  }
			  System.out.println("PK���� : "+PK);
			  if (PK ==null)
			  {
				  PK="ROWID";
			  }
			 
			  return PK;
			}
		  
		  
		  public static int getTotal(Connection connection,String owner,String table ) throws SQLException {  // ���̺� ����
		
			  String sql="  select count(1) from "+owner+"."+table;
			  
			  System.out.println(sql);
			
			  int rowCount=0;
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22		 
				
			  while (rs.next())  //DB���� �� ���
			  { 			 
				  rowCount=rs.getInt(1);
			  }
			  System.out.println("rowCount="+rowCount);
			return rowCount;  //openList ��ȯ		  
		 }
		  public static int getTotal(Connection connection,String owner,String table ,String where) throws SQLException {  // ���̺� ����
				
			  String sql="  select count(1) from "+owner+"."+table+" where "+ where;
			  
			  System.out.println(sql);
			
			  int rowCount=0;
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22		 
				
			  while (rs.next())  //DB���� �� ���
			  { 			 
				  rowCount=rs.getInt(1);
			  }
			  System.out.println("rowCount="+rowCount);
			return rowCount;  //openList ��ȯ		  
		 }

		 
		  public static ArrayList<String[]> getAll(Connection connection,String owner,String table,int beginRow,int endRow ) throws SQLException {  //���̺� 1~n���� ��ȭ
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
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //��� ����Ʈ ���� 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//�÷� ���� ������

		        System.out.println("column count : " + rsSize);

		        String[] array_column = new String[rsSize-1]; //�÷��� �迭
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//�÷��� �ֱ�
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//�迭�� ���� �ְ� 
		        
		        }
		        openList.add(array_column);//�÷��� �ֱ�Ϸ�
			  

			  System.out.println("row����: "+rsSize);
		
			  while (rs.next())  //DB���� �� ���
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//�迭�� ���� �ְ� 
					  
				  }
				  openList.add(array);// ��̸���Ʈ�� �ø���

			  }

			return openList;  //openList ��ȯ	  
		 }
		  
		  public static ArrayList<String[]> getAllExcel(Connection connection,String owner,String table) throws SQLException {  //���̺� 1~n���� ��ȭ
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
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //��� ����Ʈ ���� 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//�÷� ���� ������

		        System.out.println("column count : " + rsSize);

		        String[] array_column = new String[rsSize-1]; //�÷��� �迭
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//�÷��� �ֱ�
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//�迭�� ���� �ְ� 
		        
		        }
		        openList.add(array_column);//�÷��� �ֱ�Ϸ�
			  

			  System.out.println("row����: "+rsSize);
		
			  while (rs.next())  //DB���� �� ���
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//�迭�� ���� �ְ� 
					  
				  }
				  openList.add(array);// ��̸���Ʈ�� �ø���

			  }

			return openList;  //openList ��ȯ	  
		 }
		  
		  
		  public static ArrayList<String[]> getAllExcel(Connection connection,String owner,String table,String where) throws SQLException {  //���̺� 1~n���� ��ȭ
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
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //��� ����Ʈ ���� 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//�÷� ���� ������

		        System.out.println("column count : " + rsSize);
		        
		      

		        String[] array_column = new String[rsSize-1]; //�÷��� �迭
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//�÷��� �ֱ�
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//�迭�� ���� �ְ� 
		        
		        }
		        openList.add(array_column);//�÷��� �ֱ�Ϸ�
			  

		        
			  System.out.println("row����: "+rsSize);
		
			  while (rs.next())  //DB���� �� ���
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//�迭�� ���� �ְ� 
					  
				  }
				  openList.add(array);// ��̸���Ʈ�� �ø���

			  }

			return openList;  //openList ��ȯ	  
		 }
		 
		 
		  public static ArrayList<String[]> getAll(Connection connection,String owner,String table,int beginRow,int endRow ,String where) throws SQLException {  //���̺� 1~n���� ��ȭ
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
			
			  ArrayList<String[]> openList= new ArrayList<String[]>(); //��� ����Ʈ ���� 
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  ResultSet rs=pstmt.executeQuery();  /// ���� 22			  
			  ResultSetMetaData rsmd = rs.getMetaData();
			  
			  
		      int rsSize = rsmd.getColumnCount();//�÷� ���� ������

		        System.out.println("column count : " + rsSize);
		        
		      

		        String[] array_column = new String[rsSize-1]; //�÷��� �迭
		        
		    
		        for (int i = 2; i <= rsSize; i++)			//�÷��� �ֱ�
		        {
		        	array_column[i-2]=rsmd.getColumnName(i);//�迭�� ���� �ְ� 
		        
		        }
		        openList.add(array_column);//�÷��� �ֱ�Ϸ�
			  

		        
			  System.out.println("row����: "+rsSize);
		
			  while (rs.next())  //DB���� �� ���
			  { 
				  String[] array = new String[rsSize-1];
				  
				  for(int i=2;i<=rsSize;i++)
				  {
					  array[i-2]=rs.getString(i);//�迭�� ���� �ְ� 
					  
				  }
				  openList.add(array);// ��̸���Ʈ�� �ø���

			  }

			return openList;  //openList ��ȯ	  
		 }
		  
		  public static int updateTable(Connection connection,String owner,String table,HashMap<String,String> newMap,HashMap<String,String> oldMap ) throws SQLException
		  {
			  connection.setAutoCommit(false);
			  String newStr="";
			  for(String key:newMap.keySet()) {
				  newStr+=key+" = "+newMap.get(key)+" ,";
			  }
			  newStr=newStr.substring(0,newStr.length()-1);//�����ִ� , ����
			  //--------------------------------------------------------------------
			  String oldStr="";
			  for(String key:oldMap.keySet()) {
				  oldStr+=key+" = "+oldMap.get(key)+" and ";
			  }
			  oldStr=oldStr.substring(0,oldStr.length()-4);//�����ִ� and ����
			  oldStr=oldStr.replaceAll("= ''","is null"); //��� �ִ� ���� is null�� �ٲ��
			  //------------------------------------------------------------------------
			  
			  String sql="update "+owner+"."+table+" set "+newStr+" where "+oldStr;
			  
			  System.out.println("update:"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  
			  int r=pstmt.executeUpdate(); //����� �ο찪
			  connection.commit();
			  
			  return r;
		  }
		  
		  public static int deleteTable(Connection connection,String owner,String table,String rowid) throws SQLException
		  {
			  connection.setAutoCommit(false);
			  String sql="delete from "+owner+"."+table+" where rowid='"+rowid+"'";
			  
			  System.out.println("delete:"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  
			  int r=pstmt.executeUpdate(); //����� �ο찪
			
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
			  value=value.substring(0,value.length()-1); //�ǳ��� �ִ� �� ����
			  
			  String sql="insert into "+owner+"."+table+" values ("+value+")";
			  
			  System.out.println("insert :"+sql);
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql); // ����
			  
			  pstmt.executeUpdate();
			  connection.commit();
			
			  return sql;
		  }
       
}

