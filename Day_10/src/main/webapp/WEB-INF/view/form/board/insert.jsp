<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록 페이지</title>
</head>
<body>

<h3>게시글 등록 페이지</h3>

<form action="${pageContext.request.contextPath }/auth/board/insert.tje" 
	method="post" enctype="multipart/form-data">

<%-- 작성자의 아이디를 세션에서 추출하여 hidden타입으로 서버에 전달 --%>
<input type="hidden" name="writer" value="${sessionScope.isLogin }">

<table>
	<tr>
		<td>제목</td>
		<td><input type="text" name="title" placeholder="게시글의 제목을 입력.."></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea name="content" rows="10" cols="50"></textarea></td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td><input type="file" name="attaches" multiple></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="게시글 등록">
			<input type="reset" value="초기화">
		<td>
	</tr>
</table>

</form>

</body>
</html>