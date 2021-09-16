package com.gachidata.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gachidata.vo.Food;
import com.gachidata.service.FoodService;


@WebServlet("/foodController")
public class FoodController extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Food> lists = FoodService.cookedFoodList();
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charest=UTF-8"); // 컨턴츠 형식:html
		
		System.out.println("start foodController");//확인용
		
		PrintWriter out= resp.getWriter();
		out.println("<table>") ;
		out.println ("<tr><td>");
		out.println ("<제목></td> </tr>");
		out.println ("<tr>");
		out.println ("<td>");
		out.println (lists.get(0).getName());
		out.println ("</td>");
		out.println ("</tr>");
		out.println ("<tr>");
		out.println ("<td>");
		out.println (lists.get(1).getName());
		out.println ("</td>");
		out.println ("</tr>");
		out.println ("<tr>");
		out.println ("<td>");
		out.println (lists.get(2).getName());
		out.println ("</td>");
		out.println ("</tr>");
		out.println ("</table>");
	}
}
