<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 결과</title>
</head>
<body>

<%-- 회원탈퇴를 실행한 경우 --%>
<c:if test="${requestScope.result }" var="r">
	<h3>${requestScope.model.name } 님의 회원탈퇴가 처리 되었습니다</h3>
</c:if>

<%-- 회원탈퇴를 실패한 경우 --%>
<c:if test="${not r }">
	<h3>회원탈퇴 요청이 실패했습니다. 패스워드를 확인하세요</h3>
</c:if>

<p>메인 페이지로 <a href="<%=request.getContextPath()%>">이동</a></p>
</body>
</html>