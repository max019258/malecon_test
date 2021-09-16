package com.gachidata.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gachidata.vo.Food;
import com.gachidata.service.FoodService;


@WebServlet("/foodController2")
public class FoodController2 extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Food> lists = FoodService.cookedFoodList();
		
//		resp.setCharacterEncoding("UTF-8");
//		resp.setContentType("text/html;charest=UTF-8"); // 컨턴츠 형식:html
//		
		
//		req.setAttribute("lists0",lists.get(0).name); // request에 담아준다.
//		req.setAttribute("lists1",lists.get(1).name); // request에 담아준다
//		req.setAttribute("lists2",lists.get(2).name); // request에 담아준다
		req.setAttribute("lists",lists);
		
		RequestDispatcher rd=req.getRequestDispatcher("/view/lists.jsp");// members_map.jsp로 포위딩
		rd.forward(req,resp);
	}
}
