<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>프로젝트 생성</title>
    <%@ include file="/WEB-INF/views/fixed/header.jsp" %>
    <link href="${contextPath}/resources/css/board.css" rel="stylesheet">
</head>
<body style="overflow-x:hidden; overflow-y:hidden; margin:20px;">
<br>
<br>
<%--projectdto 만 넘기면 됩니다.--%>
<form action="creation" name="changeNote" method="post">
    <p>프로젝트명 :　<input type="text" id="projName" name="projName"/></p>
    <p>상세내용　 :　<input type="text" id="detailedDescription"  name="detailedDescription" /></p>
    <input type="hidden" id="usrId" name="usrId" value="${param.usrId}"/>
    <br>
    <button type="submit" class="btn btn-outline-secondary" >프로젝트 생성</button>
    <button tyep="button" class="btn btn-outline-secondary" onclick="opener.parent.location.reload(); window.close()">닫기</button>
</form>
</body>
</html>
