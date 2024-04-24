<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<link href="${contextPath}/resources/css/reset.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700&family=Noto+Sans+KR:wght@300;400;600&display=swap" rel="stylesheet">
<link href="${contextPath}/resources/css/layout.css" rel="stylesheet">
<link href="${contextPath}/resources/css/header.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="header_container">
		<div><img alt="logo" src="${contextPath}/resources/img/tikitaka.jpg" class="logo"></div>
		<div class="header_menu">
			<div onclick="location.href='${contextPath}/motion/mainPage'"><a><span class="material-symbols-outlined">home</span>메인</a></div>
			<div onclick="location.href='${contextPath}/document'"><a><span class="material-symbols-outlined">description</span>문서</a></div>
			<div onclick="location.href='${contextPath}/board'"><a><span class="material-symbols-outlined">view_kanban</span>보드</a></div>
			<div onclick="location.href='${contextPath}/schedule'"><a><span class="material-symbols-outlined">calendar_month</span>일정</a></div>
			<div onclick="location.href='${contextPath}/motion/videoChat'"><a><span class="material-symbols-outlined">person</span>화상채팅</a></div>
		</div>
		<div class="header_invite"><div><a><span class="material-symbols-outlined">outgoing_mail</span>초대하기</a></div></div>
	</div>
	<div class="modal fade" id="inviteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">초대 메시지</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<h5>초대할 상대의 email을 입력하세요.</h5>
				<input type="email">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-dark" onclick="javascript:sendInvitation()">전송</button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    var inviteButton = document.querySelector('.header_invite div');

    inviteButton.addEventListener('click', function() {
        var modal = new bootstrap.Modal(document.getElementById('inviteModal'));
        modal.show();
    });
});

function sendInvitation() {
    alert('전송완료');

    var modal = new bootstrap.Modal(document.getElementById('inviteModal'));
    modal.hide();
}
</script>