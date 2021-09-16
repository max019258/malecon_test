<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<b>결과</b> <br>
-------------------------<br>
<c:forEach var="members" items="${members}">
	이름 : ${members.value.getName()} <br>
	주소 : ${members.value.getAdd() } <br>
	학과 : ${members.value.getDept()} <br>
-------------------------------------------<br>
</c:forEach>

</body>
</html>