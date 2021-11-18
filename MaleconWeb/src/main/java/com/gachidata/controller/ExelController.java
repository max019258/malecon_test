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
		HttpSession session = req.getSession(true); //������ ������ ��������� ������ ��������� ����
		Connection connection=(Connection)session.getAttribute("conn"); //Ŀ�ؼ� ���������
		
		
		
		String conn_name = (String)session.getAttribute("conn_name");
		String	open_name = (String)session.getAttribute("open_name"); 
		String table_name = (String)session.getAttribute("table_name");
		String where = (String)session.getAttribute("where");
		
	
		
		ArrayList<String[]> table_content=new ArrayList<String[]>(); //����Ʈ ����
		if(where==null) { 	//where�� ���� ��
				try {
					table_content=DbConn.getAllExcel(connection, open_name, table_name); //����Ʈ ���������
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}else { 		//where�� ���� ��
			try {
				table_content=DbConn.getAllExcel(connection, open_name, table_name, where);//����Ʈ ���������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//---------------������ ���� �ڷ�� �־��� -----------------------------------------------------
		
		
		String strPath="E:\\eclipse_preject\\MaleconWeb\\src\\main\\webapp\\upload\\"+ conn_name+"_"+open_name+"_"+table_name+".xlsx";
		File file = new File(strPath); 
		if( file.exists() ){ 
				if(file.delete()){ //������ ���� �� 
						System.out.println("���ϻ��� ����"); 
						}
				else{ 
						System.out.println("���ϻ��� ����"); 
					} 
			}
		else{ 
				System.out.println("������ �������� �ʽ��ϴ�."); 
			}

		
		
		//-----------------------------������ �ִ� ���ϵ��� ��������----------------------------------------
	
		
		//.xlsx Ȯ���� ����
		XSSFWorkbook wb=new XSSFWorkbook();	//���� 2007�̻�
		XSSFSheet sh = wb.createSheet(conn_name+"_"+open_name+"_"+table_name); //��Ʈ ����
		
		XSSFRow row=null; //��
		XSSFCell cell=null; //��(��)
		
		// �׵θ���
		CellStyle csBase=wb.createCellStyle();
		csBase.setBorderLeft( BorderStyle.THIN ); 
		csBase.setBorderRight( BorderStyle.THIN ); 
		csBase.setBorderTop( BorderStyle.THIN ); 
		csBase.setBorderBottom( BorderStyle.THIN );

		//data ���
		int[] columnWidth=new int[table_content.get(0).length]; // �÷� ���̸� ���� �迭
		for (int i=0;i<columnWidth.length;i++) {
			columnWidth[i]=0;						//�ʱ�ȭ
		}
		
		for(int i=0;i<table_content.size();i++) {
			row=sh.createRow(i);	//�����
			
			
			String[] row_content= table_content.get(i);// row ���� ����
		
			for(int j=1;j<table_content.get(0).length;j++) {
				cell=row.createCell(j-1);			//�࿡�ٰ� ������
				cell.setCellValue(row_content[j]); //cell�� 
				
				cell.setCellStyle(csBase);	//����Ÿ�ϻ���
				
			
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
		
				sh.setColumnWidth(i,columnWidth[i]*500); // i��° �÷� , width 
		
		}
		
		
			try {
					FileOutputStream fOut=new FileOutputStream(strPath);
					wb.write(fOut);
			}
			catch(Exception e){
	        	System.out.println("e�߻�");
			}finally{
				RequestDispatcher rd=req.getRequestDispatcher("downloadExcel.jsp");// ������
				rd.forward(req,resp);
		    }
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//doGet(req,resp);
	}
	
}
