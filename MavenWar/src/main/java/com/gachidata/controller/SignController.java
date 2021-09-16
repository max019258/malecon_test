package com.gachidata.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gachidata.db.*;
import com.gachidata.user.*;


@WebServlet("/members")
public class SignController extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String,User> members = Db.getUsers(); //members=멤버들 들어있는 맵
		req.setAttribute("members",members);
		
		RequestDispatcher rd=req.getRequestDispatcher("/view/members_map.jsp");// members_map.jsp로 포위딩
		rd.forward(req,resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
