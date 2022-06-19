package com.gachidata.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class OpenList { //회원 목록들
	
	private static Map<String,ArrayList<String>> openList=new HashMap<String,ArrayList<String>>();
	
	public static void addOpenMap(String temp,ArrayList<String> open) //오픈리스트 추가
	{
		openList.put(temp,open); //	
	}
}
