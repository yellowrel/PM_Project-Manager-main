<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<style type="text/css">
.layout_container {
	display: grid;
	grid-template-columns: 9fr 3fr;
	height: 100vh;
}

.layout_body {
	display: grid;
	grid-template-rows:70px 10fr;
}
.layout_main{
	display: grid;
	grid-template-columns: 1fr 2.8fr;
}

.inner_side {
	display: grid;
	grid-template-rows: 35px 100px 1fr;
	height: 100%;
	padding: 20px;
	border-right: solid #212529 thin;
	gap: 20px;
}

.m_status {
	margin-top: 10px;
	font-size: 18px;
	font-weight: bold;
}

.progress {
	margin-top: 10px;
}

.inner_main {
	display: grid;
	grid-template-rows: 1fr 2fr;
}

.inner_top {
	padding: 20px;
}

.inner_todo {
	display: grid;
	grid-template-columns: 50% 50%;
	border-top: solid #212529 thin;
}

.wel_status {
	color: gray;
}

.inner_tag {
	color: gray;
}

.todo_today {
	padding: 20px;
}

.todo_today {
	padding: 20px;
	border-right: solid #212529 thin;
}

.todo_before {
	padding: 20px;
}

.item {
	display: none;
}

.card {
	margin-top: 20px;
}
</style>
</head>
<body>
	<div class="layout_container">
		<div class="layout_body">
			<div class="layout_header">
				<%@ include file="/WEB-INF/views/fixed/header.jsp" %>
			</div>
			<div class="layout_main">
				<div class="inner_side">
					<div><h3>23년 09월 25일</h3></div>
					<div class="side_wel">
						<p>test님 환영합니다.
						<p class="wel_status">상태 메세지가 없습니다.
						<p>상태설정▽
					</div>
					<div>
						<div class="m_status">전체 프로젝트 현황</div>
							<div class="progress">
							  <div class="progress-bar bg-danger" role="progressbar" style="width: 69%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						<div class="m_status">정준열님 프로젝트 현황</div>
							<div class="progress">
							  <div class="progress-bar bg-dark" role="progressbar" style="width: 71%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						<div class="m_status">이재진님 프로젝트 현황</div>
							<div class="progress">
							  <div class="progress-bar bg-dark" role="progressbar" style="width: 67%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
					</div>
				</div>
				<div class="inner_main">
					<div class="inner_top">
						<div><h2>스프링 프로젝트</h2></div>
						<div class="inner_tag"><p>#프로젝트 #협업툴<br />진행 중인 프로젝트를 팀원들과 공유해요</div>
					</div>
					<div class="inner_todo">
						<div class="todo_today">
							<div class="todo_name"><h2>오늘 할일</h2></div>
							<div class="card text-white bg-secondary mb-3" style="max-width: 30rem;">
								<div class="card-body" onclick="location.href='http://localhost:8080/tikitaka/board'">
									<h5 class="card-title" >발표 대본 만들기</h5>
								</div>
							</div>
							<div class="card text-white bg-secondary mb-3" style="max-width: 30rem;">
								<div class="card-body" onclick="location.href='http://localhost:8080/tikitaka/board'">
									<h5 class="card-title">발표 PPT 제작</h5>
								</div>
							</div>
						</div>
						<div class="todo_before">
							<div class="todo_name"><h2>D - 1</h2></div>
							<div class="card text-white bg-secondary mb-3" style="max-width: 30rem;">
								<div class="card-body" onclick="location.href='http://localhost:8080/tikitaka/board'">
									<h5 class="card-title">최종 프로젝트 발표</h5>
								</div>
							</div>						
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div class="layout_right"></div>
				</div>
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp" %>
		</div>
	</div>
	<div class="item">${projectNo}</div>
</body>
</html>