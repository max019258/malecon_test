<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<b> &lt DB 계정 연동 &gt  </b><br>
	<tr>
		<td colspan="2">------------------------------------------</td>
	</tr>
	<form action="login_action.jsp" method="post" >
	<table>
	<tr>
		<td>연결 이름 :</td> <td> <input name="conn" type="text"/></td>
	</tr>
	<tr>
		<td colspan="2">------------------------------------------</td>
	</tr>
	<tr>
		<td>호스트 이름 :</td> <td> <input name="host" type="text"/></td>
	</tr>
	<tr>
		<td>서비스 이름 :</td> <td> <input name="service" type="text"/></td>
	</tr>
	<tr>
		<td>포트  :</td> <td> <input id="spinner" name="port" type="text"/></td>
	</tr>
	<tr>
		<td>사용자 이름 :</td> <td> <input name="user" type="text"/></td>
	</tr>
	<tr>
		<td>비밀번호 :</td> <td> <input name="pwd" type="text"/></td>
	</tr>

	
	 </table>
	  <button type="submit">확인</button>
	</form>
</body>
</html>