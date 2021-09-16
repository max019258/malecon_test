package com.gachidata.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



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
               
               System.out.println(url);
               System.out.println(url);
               System.out.println("Database�� ����Ǿ����ϴ�.\n");
               
           } catch (ClassNotFoundException cnfe) {
               System.out.println("DB ����̹� �ε� ���� :"+url);
           } catch (SQLException sqle) {
               System.out.println("DB ���ӽ��� : "+url);
           } catch (Exception e) {
               System.out.println("Unkonwn error"+url);
               e.printStackTrace();
           }
           return conn;     
       }
       
		  public static void getOpen(Connection connection ) throws SQLException {  //OpenList�� ��ȯ
			  String sql="  select owner,table_name from all_tables " + 
			  		" where owner " + 
			  		" in " + 
			  		" (SELECT username FROM DBA_USERS where account_status='OPEN'and username!='SYS'and username!='SYSTEM') ";
			  System.out.println(sql);
			
			  //ArrayList<String> openList= new ArrayList<String>();
			  
			  PreparedStatement pstmt = connection.prepareStatement(sql);
			  ResultSet rs=pstmt.executeQuery(); 
			  while (rs.next())
			  { 
				  System.out.println("owner :" + rs.getString(1) ); 
				  System.out.println("table_name :" + rs.getString(2) );
				 // openList.add(rs.getString(1));				
			  }
			//return openList;  //openList ��ȯ
		  
		 }
		 
       
}

