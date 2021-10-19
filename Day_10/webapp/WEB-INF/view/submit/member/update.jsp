<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정 결과 페이지</title>
</head>
<body>

<h3>회원정보 수정 결과 페이지</h3>

<c:if test="${requestScope.updated}" var="r">
	<h4>${requestScope.newModel.name } (${requestScope.newModel.id}) 님의 회원정보 수정이 성공적으로 처리되었습니다</h4>
	<table>
		<tr>
			<td>이름</td>
			<td>${requestScope.oldModel.name } -> ${requestScope.newModel.name }</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>${requestScope.oldModel.age } -> ${requestScope.newModel.age }</td>
		</tr>
		<tr>
			<td>연락처</td>
			<td>${requestScope.oldModel.phone } -> ${requestScope.newModel.phone }</td>
		</tr>
	</table>
</c:if>

<c:if test="${not r }">
	<h4>회원정보 수정과정에서 문제가 발생했습니다</h4>
	<h4>관리자에게 연락해 주세요</h4>
</c:if>

블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a>

</body>
</html>