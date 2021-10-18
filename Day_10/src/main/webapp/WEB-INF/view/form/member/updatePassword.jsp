<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 새로운 패스워드가 검증 패스워드와 불일치하는 경우 --%>
<c:if test="${requestScope.notMatched eq 1 }">
<script type="text/javascript">
	alert("새로운 패스워드가 검증 패스워드와 일치하지 않습니다")
</script>
</c:if>
<%-- 기존 패스워드가 DB정보와 불일치하는 경우 --%>
<c:if test="${requestScope.notMatched eq 2 }">
<script type="text/javascript">
	alert("기존 패스워드와 일치하지 않습니다")
</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 패스워드 수정 페이지</title>
</head>
<body>

<h3>회원 패스워드 수정 페이지</h3>

<form action="${pageContext.request.contextPath }/member/updatePassword.tje" method="post">
<%-- 
input 태그의 hidden 타입 
- 사용자가 입력하지 않아도 서버에 전달하고자 하는 데이터를 사전에 정의하는 타입
--%>
<input type="hidden" name="id" value="${sessionScope.isLogin }"/>
	<table>
		<tr>
			<td>아이디</td>
			<td>${sessionScope.isLogin }</td>
		</tr>
		<tr>
			<td>새로운 패스워드</td>
			<td><input type="password" name="new_pw_1"></td>
		</tr>
		<tr>
			<td>새로운 패스워드 검증</td>
			<td><input type="password" name="new_pw_2"></td>
		</tr>
		<tr>
			<td>기존 패스워드</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="회원 패스워드 수정">
				<input type="reset" value="초기화">
			<td>
		</tr>
	</table>
</form>

</body>
</html>