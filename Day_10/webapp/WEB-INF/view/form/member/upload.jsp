<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원사진 업로드 페이지</title>
</head>
<body>

<h3>회원사진 업로드 페이지</h3>

<form action="${pageContext.request.contextPath}/member/upload.tje" method="post" enctype="multipart/form-data">
<table>
	<tr>
		<td>파일이름</td>
		<td><input type="text" name="name" placeholder="저장할 파일명"></td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td><input type="file" name="file" multiple></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="회원가입">
			<input type="reset" value="초기화">
		</td>
	</tr>
</table>
</form>

</body>
</html>