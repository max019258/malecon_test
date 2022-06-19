<%@ page import="com.gachidata.maleconDB.*"%>
<%@ page import="com.gachidata.db.*"%>
<%@ page import="java.util.*"%>
<%@page import="java.sql.Connection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang=''>
<head>
   <meta charset='utf-8'>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="assets/css/styles.css">
   <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
   <script src="assets/js/script.js"></script>
   <title>CSS MenuMaker</title>
</head>
<body>
<%
	session.setAttribute("reload_sideBar",null);

%>
<script>
location.href="sideBar.jsp";
</script>

</body>
<html>