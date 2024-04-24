<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <link href="resources/css/board.css" rel="stylesheet">
    <title>칸반보드</title>
    <script>
    function changeSector(boardNo) {
            var new_window_width = 350;
            var new_window_height = 270;
            var positionX = (window.screen.width / 2) - (new_window_width / 2);
            var positionY = (window.screen.height / 2) - (new_window_height / 2);

            window.open(
                "board/update?boardNo=" + boardNo, // 주소
                "섹션 이름 변경", // 제목
                "width=" + new_window_width + ", height=" + new_window_height + ", top=" + positionY + ", left=" + positionX + ",resizable=no, toolbar=no, menubar=no"
            );
        }

        function changeNote(noteNo) {
            var new_window_width = 400;
            var new_window_height = 320;
            var positionX = (window.screen.width / 2) - (new_window_width / 2);
            var positionY = (window.screen.height / 2) - (new_window_height / 2);
            window.open(
                "note/update?noteNo=" + noteNo, // 주소
                "노트 내용 변경", // 제목
                "width=" + new_window_width + ", height=" + new_window_height + ", top=" + positionY + ", left=" + positionX + ",resizable=no, toolbar=no, menubar=no"
            );
        }
    </script>
    <style type="text/css">
        .layout_container {
            display: grid;
            grid-template-columns: 9fr 3fr;
            height: 100vh;
        }

        .layout_body {
            display: grid;
            grid-template-rows:70px 10fr;
            width: 100%;
            overflow-x: auto;
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
            <main>
                <div class="add-sector">
                    <button class="add-sector" id="add_sector" onClick="location.href='board/add'"> + 섹터 추가</button>
                </div>
                <div class="lists">
                    <c:forEach var="item2" items="${boardList}">
                        <section class="column list">
                            <div style="display: flex" ;>
                                <h3 class="text-bold"
                                    onclick="changeSector(${item2.boardNo})"> ${item2.boardTitle} </h3>
                                <form method="post" action="note/add">
                                    <input type="hidden" name="boardNo" value="${item2.boardNo}"/>
                                    <button class="add-note btn">
                                        <i class="material-symbols-outlined">add_circle</i>
                                    </button>
                                </form>
                                <form name="sectorForm" id="sectorForm" method="post" action="board/delete">
                                    <button type="submit" class="btn">
                                        <i class="material-symbols-outlined">cancel</i>
                                    </button>
                                    <input type="hidden" id="boardNo" name="boardNo" value="${item2.boardNo}"/>
                                </form>
                            </div>

                            <div id="sortable" class="list-body">
                                <c:forEach var="item" items="${noteList}">
                                    <c:if test="${item.boardNo eq item2.boardNo}">
                                        <div draggable="true" class="kanban-card">
                                            <form name="noteForm" id="noteForm" method="post" action="note/delete">
                                                <input type="hidden" name="noteNo" id="noteNo" value="${item.noteNo}"/>
                                                <button class="del-note">
                                                    <i class="material-symbols-outlined">do_not_disturb_on</i>
                                                </button>
                                                <button class="btn default update-note" type="button"
                                                        onclick="changeNote(${item.noteNo})">
                                                    <i class="material-symbols-outlined">settings</i>
                                                </button>
                                            </form>
                                            <span>
                                                    ${item.noteTitle}
                                            </span>
                                            <span>
                                                기간 : <fmt:formatDate value="${item.startDay}"
                                                                     pattern="yy-MM-dd"/> ~  <fmt:formatDate
                                                    value="${item.endDay}" pattern="yy-MM-dd"/>
                                            </span>
                                            <span>
                                                담당자 :  ${item.charger}
                                            </span>
                                            <div><%--상하좌우 이동 버튼 만들어야함--%> <%--함수 호출이 아니라 콘트롤러 호출해야함+ 매개변수 넘겨야함--%>
                                                <button class="btn default update-note" type="button"
                                                        onclick="location.href='noteMoveBoard?dir=1&noteNo=${item.noteNo}&projNo=${item.projNo}'">
                                                    <i class="material-symbols-outlined">Chevron_Left</i>
                                                </button>
                                                <button class="btn default update-note" type="button"
                                                        onclick="location.href='noteMoveBoard?dir=2&noteNo=${item.noteNo}&projNo=${item.projNo}'">
                                                    <i class="material-symbols-outlined">navigate_next</i>
                                                </button>
                                                <button class="btn default update-note" type="button"
                                                        onclick="location.href='moveNote?dir=1&noteNo=${item.noteNo}&projNo=${item.projNo}'">
                                                    <i class="material-symbols-outlined">Expand_Less</i>
                                                </button>
                                                <button class="btn default update-note" type="button"
                                                        onclick="location.href='moveNote?dir=2&noteNo=${item.noteNo}&projNo=${item.projNo}'">
                                                    <i class="material-symbols-outlined">Expand_more</i>
                                                </button>
                                            </div><%--상하좌우 버튼 end--%>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <br>
                            </div>
                        </section>
                    </c:forEach>
                </div>
            </main>
        </div>
    </div>
    <div class="layout_side">
        <%@ include file="/WEB-INF/views/fixed/side.jsp" %>
    </div>
</div>

</body>
</html>
