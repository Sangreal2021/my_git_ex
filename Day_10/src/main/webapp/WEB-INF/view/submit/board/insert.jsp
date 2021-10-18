<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 입력 결과 페이지</title>
</head>
<body>

<h3>게시글 입력 결과 페이지</h3>

<c:if test="${requestScope.inserted }" var="r">
	<h4>${requestScope.model.title } 게시글이 성공적으로 등록처리되었습니다</h4>
	
	<h4>${requestScope.files.size() } 개의 첨부파일이 저장되었습니다</h4>
	
	<c:forEach var="f" items="${requestScope.files}" varStatus="vs">
		<h5>${vs.count } 번째 첨부파일 : ${f.path }</h5>
	</c:forEach>
</c:if>

<c:if test="${not r }">
	<h4>게시글 입력과정에서 문제가 발생했습니다</h4>
	<h4>관리자에게 연락해 주세요</h4>
</c:if>

블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a>

</body>
</html>