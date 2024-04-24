<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<head>
<meta charset="UTF-8">
<title>Document</title>
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/css/documentEditor.css" >
<!-- TOAST UI Editor CDN(JS) -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<!-- TOAST UI Editor CDN(CSS) -->
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

</head>
<body>
	<div class="layout_container">
		<div class="layout_body">
			<div class="layout_header">
				<%@ include file="/WEB-INF/views/fixed/header.jsp"%>
			</div>
			<div class="doc_container">
				<div class="doc_header">
					<div class="doc_name">
						<p>문서 수정 페이지
					</div>
				</div>
				<form action="/document/edit" method="post">
				<div class="doc_main">
				<!-- Title -->
					 <div class="doc_title">
						<div class="input-group input-group-lg">
							<span class="input-group-text" id="inputGroup-sizing-lg">Title</span>
							<input type="text" class="form-control"  id="docTitleInput" name='docTitle'
							aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg"  value="${doc.docTitle}">
						</div>
					</div>
					<div id="editor" name="docContent">${doc.docContent}</div>
					<!-- Button -->
					<div class="doc_btnWrap">
						<button type="button" class="btn btn-danger" id="cancelButton">취소</button>
						<button type="submit" class="btn btn-dark" id="updateButton">수정</button>
					</div>
				</div>
				</form>
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp"%>
		</div>
	</div>
	</body>
<script type ="text/javascript">

$(document).ready(function() {
    // 에디터 스크립트, 스타일을 로드한 후에 에디터 생성
    var editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        height: '600px',
        readOnly: false, // 수정이 가능해야 하므로 false 로 설정
    });

    $("#cancelButton").click(function() {
        window.location.href = '/tikitaka/document';
    });

    // 현재 페이지의 문서 페이지 번호 추출
    var currentPageURL = window.location.href;
    var docPageNo = currentPageURL.split('/').pop(); // 루트부터의 상대 경로

    $("#updateButton").click(function(event) {
        event.preventDefault();

        var docTitle = $("#docTitleInput").val();
        var docContent = editor.getMarkdown(); // 에디터 내용 가져오기

        // 유효성 검사
        if (!docTitle.trim()) {
            docTitle = "제목 없는 문서";
        }

        $.ajax({
            url: '/tikitaka/document/edit/', // 수정 페이지로 PUT 요청을 보냄
            type: 'POST',
            data: JSON.stringify({
                docPageNo: docPageNo,
                docTitle: docTitle,
                docContent: docContent
            }),
            contentType: 'application/json; charset=utf-8',
            success: function(response) {
                alert('문서가 성공적으로 수정되었습니다.');
                window.location.href = '/tikitaka/document'; // 수정 후 문서 페이지로 리디렉션
            },
            error: function(xhr, status, error) {
                alert('문서 수정 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });
});


</script>
</html>