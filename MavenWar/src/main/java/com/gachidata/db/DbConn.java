package com.gachidata.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gachidata.user.User;

public class DbConn 
{
	/* public static Connection dbConn; */
   
       public static Connection getConnection()
       {
           Connection conn = null;
           try {
               String user = "scott"; 
               String pw = "tiger";
               String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE"; //XE =SID
               
               Class.forName("oracle.jdbc.driver.OracleDriver");  //jdbc드라이버 로딩      
               conn = DriverManager.getConnection(url, user, pw);
               
               System.out.println("Database에 연결되었습니다.\n");
               
           } catch (ClassNotFoundException cnfe) {
               System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
           } catch (SQLException sqle) {
               System.out.println("DB 접속실패 : "+sqle.toString());
           } catch (Exception e) {
               System.out.println("Unkonwn error");
               e.printStackTrace();
           }
           return conn;     
       }
       
       public void insert(User user) throws SQLException  {
    	   String sql ="insert into si values(?,?,?)";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1,user.getName());
    	   pstmt.setString(2,user.getAdd());
    	   pstmt.setString(3,user.getDept());
    	   
    	   pstmt.executeUpdate();
       }
       
       public void update(User user) throws SQLException {
    	   
    	   String sql="update si set dept=?";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1, user.getDept());
    	   pstmt.executeUpdate();
       }
       
       public void delete(User user) throws SQLException{
    	   String sql="delete from si where dept=?";
    	   PreparedStatement pstmt = getConnection().prepareStatement(sql);
    	   pstmt.setString(1, user.getDept());
    	   pstmt.executeUpdate();
       }
       
       public void select() throws SQLException
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

