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
	
 
	public static XSSFWorkbook getWorkbook(String path) throws IOException{	// workbook가지고 오는 
		FileInputStream file = new FileInputStream(path); 
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		return workbook;
	}
	
	public static Map<String,String> sheetName(XSSFWorkbook workbook){ // 시트 이름에서 conn,owner,name 떼오는 역할
		String sheet =workbook.getSheetName(0);
		String[] sheet_arr= sheet.split("_"); // _기준으로 나눈다.
		Map<String,String> result = new HashMap<String,String>();
		result.put("conn", sheet_arr[0]);
		result.put("owner", sheet_arr[1]);
		result.put("table", sheet_arr[2]);
		return result;	
	}
	
	public static String[][] tableContent(XSSFWorkbook workbook){ // 테이블 내용 가지고 오기 
		XSSFSheet sheet=workbook.getSheetAt(0); // 시트를 가지고 온다.
		// row 0은 컬럼명
	
		int rows=sheet.getPhysicalNumberOfRows();// 행의 수
		int cells=sheet.getRow(0).getPhysicalNumberOfCells(); //컬럼명 개수 (row=0)
		
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
					//타입별로 내용 읽기 
					switch (cell.getCellType()){ 
//					case FORMULA: //수식
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
	

	
	
	
	