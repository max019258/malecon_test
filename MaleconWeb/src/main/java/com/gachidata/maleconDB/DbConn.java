package com.gachidata.maleconDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gachidata.user.User;



public class DbConn
{
	 private static Connection conn=getConnection();
			 
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
               System.out.println(url);
               System.out.println("Database에 연결되었습니다.\n");
               
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
       public static void insert() throws SQLException  {
    	   String sql ="insert into malecon values(?,?,?)";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1,user.getName());
    	   pstmt.setString(2,user.getAdd());
    	   pstmt.setString(3,user.getDept());
    	   
    	   pstmt.executeUpdate();
       }
       public void update() throws SQLException {
    	   
    	   String sql="update si set dept=?";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1, user.getDept());
    	   pstmt.executeUpdate();
       }
       
       public void delete() throws SQLException{
    	   String sql="delete from si where dept=?";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1, user.getDept());
    	   pstmt.executeUpdate();
       }
       
       public static void select() throws SQLException
       {
    	   String sql="select * from si";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   ResultSet rs=pstmt.executeQuery();
    	   while (rs.next()){
				System.out.println("이름 " + rs.getString(1)
				+ ", 주소 " + rs.getString(2) + ", 부서 "
				+ rs.getString(3) );
    	   }
       }
      
		 
       
}

