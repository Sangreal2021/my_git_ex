<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 게시글 목록 페이지</title>
</head>
<body>

<h3>전체 게시글 목록 페이지</h3>

<c:if test="${empty list}" var="r">
	<h3>등록된 게시글이 없습니다</h3>
</c:if>

<c:if test="${not r }">
	<table border="1">
		<tr>
			<td>게시글ID</td>
			<td>제목</td>
			<td>작성자ID</td>
			<td>작성일자</td>
			<td>조회수</td>
		</tr>
		
		<c:forEach var="board" items="${list }">
			<tr>
				<td>${board.id }</td>
				<td><a href="${pageContext.request.contextPath }/auth/board/detail.tje?id=${board.id}">${board.title }</a></td>
				<td>${board.writer }</td>
				<td>${board.regDate }</td>
				<td>${board.readCount }</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a>
</body>
</html>