package com.gachidata.db;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
 
public class OracleTest 
{
    public static void main(String args[])
    {
        Connection conn = null; // DB연결된 상태(세션)을 담은 객체
        PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
        ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체
        
        try {
            // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면
            // 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
            String quary = "SELECT * FROM EMP";
            
            conn = DbConn.getConnection();
            pstm = conn.prepareStatement(quary);
            rs = pstm.executeQuery();

            
            System.out.println("EMPNO ENAME JOB MGR HIREDATE SAL COMM DEPTNO");
            System.out.println("============================================");
            
            while(rs.next()){
                int empno = rs.getInt(1);
                //int empno = rs.getInt("empno"); 숫자 대신 컬럼 이름을 적어도 된다.
                String ename = rs.getString(2);
                String job = rs.getString(3);
                int mgr = rs.getInt(4);
                java.sql.Date hiredate = rs.getDate(5); // Date 타입 처리
                int sal = rs.getInt(6);
                int comm = rs.getInt(7);
                int deptno = rs.getInt(8);
                
                String result = empno+" "+ename+" "+job+" "+mgr+" "+hiredate+" "+sal+" "+comm+" "+deptno;
                System.out.println(result);
            }
            
        } catch (SQLException sqle) {
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();
            
        }finally{
            // DB 연결을 종료한다.
            try{
                if ( rs != null ){rs.close();}   
                if ( pstm != null ){pstm.close();}   
                if ( conn != null ){conn.close(); }
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
            
        }
    }
}


