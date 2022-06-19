package com.gachidata.maleconDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class MaleconDb
{
	 private static Connection Conn=getConnection();
			 
       public  static Connection getConnection()
       {
           Connection conn = null;
           String url = null;
           try {
               String user = "system"; 
               String pw = "oracle";
               url = "jdbc:oracle:thin:@"+"localhost"+":"+"1521"+":"+"XE"; //XE =SID
               
               Class.forName("oracle.jdbc.driver.OracleDriver");  //jdbc드라이버 로딩      
               conn = DriverManager.getConnection(url, user, pw);
               
               System.out.println(url);
           
               System.out.println("malcon Database에 연결되었습니다.\n");
               
           } catch (ClassNotFoundException cnfe) {
               System.out.println("DB 드라이버 로딩 실패 :"+url);
           } catch (SQLException sqle) {
               System.out.println("DB 접속실패 : "+url);
           } catch (Exception e) {
               System.out.println("Unkonwn error"+url);
               e.printStackTrace();
           }
           return conn;     
       }
       public static void insert(String conn,String host, String service,String port,String user,String pwd) throws SQLException  {
    	   String sql ="insert into malecon values(?,?,?,?,?,?)";
    	   PreparedStatement pstmt = Conn.prepareStatement(sql);
    	   pstmt.setString(1,conn);
    	   pstmt.setString(2,host);
    	   pstmt.setString(3,service);
    	   pstmt.setString(4,port);
    	   pstmt.setString(5,user);
    	   pstmt.setString(6,pwd);
    	 
    	   pstmt.executeUpdate();
       }
//       public void update() throws SQLException {
//    	   
//    	   String sql="update si set dept=?";
//    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
//    	   pstmt.setString(1, user.getDept());
//    	   pstmt.executeUpdate();
//       }
//       
//       public void delete() throws SQLException{
//    	   String sql="delete from si where dept=?";
//    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
//    	   pstmt.setString(1, user.getDept());
//    	   pstmt.executeUpdate();
//       }
//       
       public static ArrayList<String> selectConn() throws SQLException  // 연결이름만 가지고 오는 함수
       {
    	   String sql="select connname from malecon group by connname order by connname";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   ResultSet rs=pstmt.executeQuery();
    	   
    	   ArrayList<String> temp = new ArrayList<String>();
    	   
    	   while (rs.next()){
    		 temp.add(rs.getString(1));    			   
    	   }
    	   return temp;
       }
       
       public static ArrayList<String> selectOpen(String connName) throws SQLException  // 오픈된 친구만 가지고 오는 함수
       {
    	   String sql="select openowner from malecon where connname='"+connName+"' group by openowner";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   ResultSet rs=pstmt.executeQuery();
    	   
    	   ArrayList<String> temp = new ArrayList<String>();
    	   
    	   while (rs.next()){
    		 temp.add(rs.getString(1));    			   
    	   }
    	   return temp;
       }
       
       public static ArrayList<String> selectTable(String connName,String openOwner) throws SQLException  // 테이블만 가지고 오는 함수
       {
    	   String sql="select tablename from malecon where connname='"+connName+"' and openowner='"+openOwner+"'";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   ResultSet rs=pstmt.executeQuery();
    	   
    	   ArrayList<String> temp = new ArrayList<String>();
    	   
    	   while (rs.next()){
    		 temp.add(rs.getString(1));    			   
    	   }
    	   return temp;
       }
      
       public static ArrayList<String> selectAll(String connName) throws SQLException  //전체적인거를 가지고오는 함수
       {
    	   String sql="select * from malecon where connname='"+connName+"'";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   ResultSet rs=pstmt.executeQuery();
    	   
    	   ArrayList<String> temp = new ArrayList<String>();
    	   
    	   while (rs.next()){
    		 temp.add(rs.getString(1));
    		 temp.add(rs.getString(2));
    		 temp.add(rs.getString(3));
    		 temp.add(rs.getString(4));
    		 temp.add(rs.getString(5));
    		 temp.add(rs.getString(6));  
    	   }
    	   return temp;
       }
       
//       public static ArrayList<String> selectTable(String connName,String openOwner ,String tableName) throws SQLException  //전체적인거를 가지고오는 함수
//       {
//    	   String sql="select * from malecon where connname='"+connName+"' and openowner='"+openOwner+"' and tablename='"+tableName+"'";
//    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
//    	   ResultSet rs=pstmt.executeQuery();
//    	   
//    	   ArrayList<String> temp = new ArrayList<String>();
//    	   
//    	   while (rs.next()){
//    		 temp.add(rs.getString(1));
//    		 temp.add(rs.getString(2));
//    		 temp.add(rs.getString(3));
//    		 temp.add(rs.getString(4));
//    		 temp.add(rs.getString(5));
//    		 temp.add(rs.getString(6));
//  
//    	   }
//    	   return temp;
//       }
      
       
		 
       
}

