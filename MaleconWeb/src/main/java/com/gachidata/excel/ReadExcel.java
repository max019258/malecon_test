package com.gachidata.excel;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
 
	public static XSSFWorkbook getWorkbook(String path) throws IOException{	// workbook������ ���� 
		FileInputStream file = new FileInputStream(path); 
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
	}
	
	public static Map<String,String> sheetName(XSSFWorkbook workbook){ // ��Ʈ �̸����� conn,owner,name ������ ����
		String sheet =workbook.getSheetName(0);
		String[] sheet_arr= sheet.split("_"); // _�������� ������.
		Map<String,String> result = new HashMap<String,String>();
		result.put("conn", sheet_arr[0]);
		result.put("owner", sheet_arr[1]);
		result.put("table", sheet_arr[2]);
		return result;	
	}
	
	public static String[][] tableContent(XSSFWorkbook workbook){ // ���̺� ���� ������ ���� 
		XSSFSheet sheet=workbook.getSheetAt(0); // ��Ʈ�� ������ �´�.
		// row 0�� �÷���
	
		int rows=sheet.getPhysicalNumberOfRows();// ���� ��
		int cells=sheet.getRow(0).getPhysicalNumberOfCells(); //�÷��� ���� (row=0)
		
		String[][] result= new String[rows][cells];
		
		for(int rowindex=0;rowindex<rows;rowindex++) {
				XSSFRow row=sheet.getRow(rowindex);
			
			for(int columnindex=0;columnindex<=cells;columnindex++)
			{
				XSSFCell cell=row.getCell(columnindex);
				String value="";
				
				if(cell==null){ 
					continue; 
				}else{ 
					//Ÿ�Ժ��� ���� �б� 
					switch (cell.getCellType()){ 
//					case FORMULA: //����
//						value=cell.getCellFormula(); 
//						break; 
					case NUMERIC: 
						value=cell.getNumericCellValue()+""; 
						break; 
					case STRING: 
						value=cell.getStringCellValue()+""; 
						break; 
//					case BLANK: 
//						value=cell.getBooleanCellValue()+""; 
//						break; 
					case ERROR: 
						value=cell.getErrorCellValue()+""; 
						break; 
					}
				result[rowindex][columnindex]=value;
				}	
			}
		
		}
		return result;
	}
}
	

	
	
	
	