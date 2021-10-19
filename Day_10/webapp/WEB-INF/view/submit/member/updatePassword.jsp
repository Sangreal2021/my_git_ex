<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 패스워드 수정 결과 페이지</title>
</head>
<body>

<h3>회원 패스워드 수정 결과 페이지</h3>

<c:if test="${requestScope.updated}" var="r">
	<h4>${requestScope.model.id} 님의 패스워드 수정이 성공적으로 처리되었습니다</h4>
</c:if>

<c:if test="${not r }">
	<h4>회원 패스워드 수정과정에서 문제가 발생했습니다</h4>
	<h4>관리자에게 연락해 주세요</h4>
</c:if>

블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a>

</body>
</html>