<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원사진 업로드 결과 페이지</title>
</head>
<body>

<h3>회원사진 업로드 결과 페이지</h3>

<c:if test="${requestScope.uploaded }" var="r">
	<h4>${requestScope.model.name } (${requestScope.model.id }) 님의 업로드가 성공적으로 처리되었습니다</h4>
</c:if>

<c:if test="${not r }">
	<h4>업로드 과정에서 문제가 발생했습니다</h4>
	<h4>관리자에게 문의하세요</h4>
</c:if>

블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a>
</body>
</html>