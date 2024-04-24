<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</style>
</head>
<body>
	<div class="layout_container">
		<div class="layout_body">
			<div class="layout_header">
				<%@ include file="/WEB-INF/views/fixed/header.jsp" %>
			</div>
			<div class="layout_main">
							
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp" %>
		</div>
	</div>
</body>
</html>