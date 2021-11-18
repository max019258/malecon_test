package com.gachidata.controller;

import java.awt.Color;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFrame;

import org.apache.catalina.Session;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gachidata.db.*;


@WebServlet("/exel")
public class ExelController extends HttpServlet {
	   
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true); //세션이 있으면 가지고오고 없으면 가지고고지 않음
		Connection connection=(Connection)session.getAttribute("conn"); //커넥션 가지고오기
		
		
		
		String conn_name = (String)session.getAttribute("conn_name");
		String	open_name = (String)session.getAttribute("open_name"); 
		String table_name = (String)session.getAttribute("table_name");
		String where = (String)session.getAttribute("where");
		
	
		
		ArrayList<String[]> table_content=new ArrayList<String[]>(); //리스트 선언
		if(where==null) { 	//where이 있을 때
				try {
					table_content=DbConn.getAllExcel(connection, open_name, table_name); //리스트 가지고오기
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}else { 		//where이 없을 때
			try {
				table_content=DbConn.getAllExcel(connection, open_name, table_name, where);//리스트 가지고오기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//---------------엑셀에 넣을 자료들 넣었음 -----------------------------------------------------
		
		
		String strPath="E:\\eclipse_preject\\MaleconWeb\\src\\main\\webapp\\upload\\"+ conn_name+"_"+open_name+"_"+table_name+".xlsx";
		File file = new File(strPath); 
		if( file.exists() ){ 
				if(file.delete()){ //파일이 있을 때 
						System.out.println("파일삭제 성공"); 
						}
				else{ 
						System.out.println("파일삭제 실패"); 
					} 
			}
		else{ 
				System.out.println("파일이 존재하지 않습니다."); 
			}

		
		
		//-----------------------------기존에 있는 파일들을 삭제했음----------------------------------------
	
		
		//.xlsx 확장자 지원
		XSSFWorkbook wb=new XSSFWorkbook();	//엑셀 2007이상
		XSSFSheet sh = wb.createSheet(conn_name+"_"+open_name+"_"+table_name); //시트 생성
		
		XSSFRow row=null; //행
		XSSFCell cell=null; //열(셀)
		
		// 테두리선
		CellStyle csBase=wb.createCellStyle();
		csBase.setBorderLeft( BorderStyle.THIN ); 
		csBase.setBorderRight( BorderStyle.THIN ); 
		csBase.setBorderTop( BorderStyle.THIN ); 
		csBase.setBorderBottom( BorderStyle.THIN );

		//data 출력
		int[] columnWidth=new int[table_content.get(0).length]; // 컬럼 넓이를 담을 배열
		for (int i=0;i<columnWidth.length;i++) {
			columnWidth[i]=0;						//초기화
		}
		
		for(int i=0;i<table_content.size();i++) {
			row=sh.createRow(i);	//행생성
			
			
			String[] row_content= table_content.get(i);// row 내용 선언
		
			for(int j=1;j<table_content.get(0).length;j++) {
				cell=row.createCell(j-1);			//행에다가 셀생성
				cell.setCellValue(row_content[j]); //cell값 
				
				cell.setCellStyle(csBase);	//셀스타일생성
				
			
				int content_length=0;						
				if(row_content[j]!=null) {
					content_length=row_content[j].length();
				}
				
				if(columnWidth[j-1]<content_length) {
					columnWidth[j-1]=content_length;
				}			
			}
			
		}
	
		for(int i=0;i<columnWidth.length;i++) {
		
				sh.setColumnWidth(i,columnWidth[i]*500); // i번째 컬럼 , width 
		
		}
		
		
			try {
					FileOutputStream fOut=new FileOutputStream(strPath);
					wb.write(fOut);
			}
			catch(Exception e){
	        	System.out.println("e발생");
			}finally{
				RequestDispatcher rd=req.getRequestDispatcher("downloadExcel.jsp");// 포위딩
				rd.forward(req,resp);
		    }
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//doGet(req,resp);
	}
	
}
