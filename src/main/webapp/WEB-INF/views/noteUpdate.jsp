<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>노트명 변경</title>

    <%@ include file="/WEB-INF/views/fixed/header.jsp" %>
    <link href="${contextPath}/resources/css/board.css" rel="stylesheet">
    </head>
<body style="overflow-x:hidden; overflow-y:hidden; margin:20px;">
<br>
<form action="updateConfirm" name="changeNote" method="post">
    <input type="hidden" value="${param.noteNo}" id="noteNo" name="noteNo" />
    <p>노트명 : <input type="text" id="noteTitle" name="noteTitle"/></p>
    <p>시작일 : <input type="date" id="noteStartday"  name="noteStartday" value="2000-01-01" /></p>
    <p>종료일 : <input type="date" id="noteEndday"  name="noteEndday" value="2100-01-01" /></p>
    <p>담당자 : <input type="text" id="noteCharger" name="noteCharger"/></p>

    <button type="submit"  class="btn btn-outline-secondary" >노트 업데이트</button>
    <button tyep="button"  class="btn btn-outline-secondary" onclick="opener.parent.location.reload(); window.close()">닫기</button>
</form>

</body>
</html>
