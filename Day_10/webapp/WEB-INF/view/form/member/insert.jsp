<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
</head>
<body>

<%-- 중복된 ID를 입력하여 회원가입 버튼을 클릭한 경우 경고창을 출력 --%>
<c:if test="${not empty requestScope.duplicated }">
<script type="text/javascript">
	alert("${requestScope.model.id}는 기존에 사용 중 입니다")
</script>
</c:if>

<h3>회원가입 페이지</h3>

<form action="${pageContext.request.contextPath }/member/insert.tje" method="post">

<table>
	<tr>
		<td>아이디</td>
		<td><input type="text" name="id" placeholder="아이디는 10글자 이내로"></td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<td>이름</td>
		<td><input type="text" name="name" placeholder="이름는 5글자 이내로" value="${requestScope.model.name }"></td>
	</tr>
	<tr>
		<td>나이</td>
		<td><input type="text" name="age" placeholder="나이는 숫자로" value="${requestScope.model.age }"></td>
	</tr>
	<tr>
		<td>연락처</td>
		<td><input type="text" name="phone" placeholder="000-0000-0000" value="${requestScope.model.phone }"></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="회원가입">
			<input type="reset" value="초기화">
		<td>
	</tr>
</table>

</form>

</body>
</html>