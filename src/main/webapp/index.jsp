<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

	<h1>HELLO WORLD!!!</h1>
	<a href="schedule">일정</a>
	<a href="document">문서</a>
	<button onclick="javascript:location.href='motion/getproj'">이동</button>
	<button onclick="javascript:location.href='project/list'">이동</button>
	<button onclick="javascript:location.href='motion/login'">로그인</button> 
	<button onclick="javascript:location.href='motion/signUp'">회원가입</button>
	<button onclick="javascript:location.href='motion/findId'">아이디찾기</button>
	<button onclick="javascript:location.href='motion/findPwd'">비밀번호찾기</button>
	<button onclick="javascript:location.href='motion/main'">메인</button>
	<button onclick="javascript:location.href='board'">보드</button>
	<button onclick="javascript:location.href='motion/videoChat'">화상채팅 화면공유</button>
	<button onclick="javascript:location.href='motion/mainPage'">메인페이지</button>
	
</body>
</html>

