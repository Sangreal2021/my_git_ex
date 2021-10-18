<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정 페이지</title>
</head>
<body>

<h3>회원정보 수정 페이지</h3>

<form action="${pageContext.request.contextPath}/member/update.tje" method="post">
<%-- 
 input 태그의 hidden 타입 
 - 사용자가 입력하지 않아도 서버에 전달하고자 하는 데이터를 사전에 정의하는 타입 
--%>
<input type="hidden" name="id" value="${model.id}">

<table>
	<tr>
		<td>아이디</td>
		<td>${ model.id }</td>
	</tr>
	<tr>
		<td>이름</td>
		<td><input type="text" name="name" value="${requestScope.model.name}"></td>
	</tr>
	<tr>
		<td>나이</td>
		<td><input type="text" name="age" value="${requestScope.model.age}"></td>
	</tr>
	<tr>
		<td>연락처</td>
		<td><input type="text" name="phone" value="${requestScope.model.phone}"></td>
	</tr>
	<tr>
		<td>가입날자</td>
		<td>${requestScope.model.regDate}</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="회원수정">
			<input type="reset" value="초기화">
		</td>
	</tr>
</table>

</form>

</body>
</html>