<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty param.isFailed }">
<script type="text/javascript">
	alert("로그인에 실패했습니다");
</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>

<h3>로그인 페이지</h3>

<form action="${pageContext.request.contextPath }/member/login.tje" method="post">
<table>
	<tr>
		<td>아이디</td>
		<td><input type="text" name="id" value="${saved_id }"></td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<td colspan="2">
			<label>아이디 저장
			<input type="checkbox" name="isSaveId" value="true" ${saved_checked }>
			</label>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="로그인">
			<input type="reset" value="초기화">
		<td>
	</tr>
</table>

</form>

</body>
</html>