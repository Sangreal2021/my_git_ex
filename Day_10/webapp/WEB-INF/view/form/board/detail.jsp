<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 확인 페이지</title>
</head>
<body>

<h3>게시글 확인 페이지</h3>

<table>
	<tr>
		<td>ID</td>
		<td>${model.id }</td>
	</tr>
	<tr>
		<td>제목</td>
		<td>${model.title }</td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="10" cols="20">${model.content }</textarea></td>
	</tr>
	<tr>
		<td>작성일자</td>
		<td>${model.regDate }</td>
	</tr>
	<tr>
		<td>마지막 수정일자</td>
		<td>${model.lastUpdateTime }</td>
	</tr>
	<tr>
		<td>조회수</td>
		<td>${model.readCount }</td>
	</tr>
</table>

<p>블로그 메인 페이지로 <a href="${pageContext.request.contextPath }">이동</a></p>
<p>게시글 목록 페이지로 <a href="${pageContext.request.contextPath }/auth/board/list.tje">이동</a></p>
</body>
</html>