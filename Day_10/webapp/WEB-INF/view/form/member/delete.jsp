<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>
	
<h3>회원 탈퇴</h3>

<form action="<%=request.getContextPath()%>/auth/member/delete" method="post">
	<h4>회원 탈퇴를 진행하시겠습니까? 패스워드를 입력해 주세요</h4>
	<%-- 세션에 저장된 ID를 사용하여 숨겨진 파라미터값을 설정 --%>
	<input type="hidden" name="id" value="${sessionScope.loginID }">
	<input type="password" name="password">
	<input type="submit" value="탈퇴">
</form>

<p>메인 페이지로 <a href="<%=request.getContextPath()%>">이동</a></p>
</body>
</html>