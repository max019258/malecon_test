package com.gachidata.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class OpenList { //ȸ�� ��ϵ�
	
	private static Map<String,ArrayList<String>> openList=new HashMap<String,ArrayList<String>>();
	
	public static void addOpenMap(String temp,ArrayList<String> open) //���¸���Ʈ �߰�
	{
		openList.put(temp,open); //	
	}
}
