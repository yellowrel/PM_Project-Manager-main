<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>섹터명 변경</title>
    <%@ include file="/WEB-INF/views/fixed/header.jsp" %>
    <link href="${contextPath}/resources/css/board.css" rel="stylesheet">
    </head>
<body style="overflow-x:hidden; overflow-y:hidden; margin:20px;">
<form action="updateConfirm" name="changeSector" method="post">
    <br><br>
    <p>섹터명 : <input type="text" id="boardTitle" name="boardTitle"/></p>
    <input type="hidden" value="${param.boardNo}" id="boardNo" name="boardNo" />
    <div class="m-5">
    <button type="submit" class="btn btn-outline-secondary">섹터명 변경</button>
    <button tyep="button" class="btn btn-outline-secondary" onclick="opener.parent.location.reload(); window.close()">닫기</button>
    </div>
</form>

</body>
</html>
