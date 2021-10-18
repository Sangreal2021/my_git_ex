<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 블로그 메인페이지</title>
</head>
<body>

<h3>블로그 메인페이지 입니다</h3>

<c:if test="${not empty sessionScope.isLogin }">
	<h4>${sessionScope.isLogin } 님 환영합니다</h4>
</c:if>
	
<ul>
	<%--  
		로그인 정보는 세션 내부에 isLogin 이름으로 저장됩니다
		아래의 분기문은 세션 영역에 isLogin 정보가 존재하지 않으면 true 로 처리됩니다.
		그리고 그 결과는 pageScope 영여에 r 이름으로 저장됩니다
	--%>
	<c:if test="${empty sessionScope.isLogin }" var="r" scope="page">
		<li><a href="${pageContext.request.contextPath }/member/login.tje">로그인</a></li>
		<li><a href="<%=request.getContextPath()%>/member/insert.tje">회원가입</a></li>
	</c:if>
	<%--
		아래의 분기문은 앞서 실행한 if문의 조건을 사용하여
		false인 경우에만 실행됩니다. (로그인 정보가 기록된 사용자에게 보여지는 메뉴)			
	--%>
	<c:if test="${not pageScope.r }">
	
		<li><a href="${pageContext.request.contextPath }/auth/board/list.tje">게시글 보기</a></li>
		<li><a href="${pageContext.request.contextPath }/auth/board/insert.tje">게시글 등록</a></li>
		
		<li><a href="${pageContext.request.contextPath }/member/update.tje">회원정보 수정</a></li>
		<li><a href="${pageContext.request.contextPath }/member/updatePassword.tje">패스워드 수정</a></li>
		<li><a href="${pageContext.request.contextPath }/member/logout.tje">로그아웃</a></li>
		<li><a href="${pageContext.request.contextPath }/member/delete.tje">회원탈퇴</a></li>
	</c:if>
</ul>

</body>
</html>











