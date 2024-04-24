<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<head>
<meta charset="UTF-8">
<title>Document</title>
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<%-- <script type="text/javascript" src="${contextPath}/resources/ckeditor/ckeditor.js"></script> --%>

<!-- TOAST UI Editor CDN(JS) -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<!-- TOAST UI Editor CDN(CSS) -->
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<link rel="stylesheet" href="${contextPath}/resources/ckeditor/skins/moono-lisa/editor_gecko.css">
<link rel="stylesheet" href="${contextPath}/resources/css/documentEditor.css" >
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
						<p>문서페이지
					</div>
				</div>
				<form action="/document/add" method="post">
				<div class="doc_main">
				<!-- Title -->
					 <div class="doc_title">
						<div class="input-group input-group-lg">
							<span class="input-group-text" id="inputGroup-sizing-lg">Title</span>
							<input type="text" class="form-control"  id="docTitleInput" name='docTitle'
							aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
						</div>
					</div>
					
					<!-- Content -->
					<!-- <div class="doc_text">
					<textarea id="editorTextarea" name="docContent" class="createEditor"></textarea>
					</div> -->
					<div id="editor" name = "docContent"></div>
					<!-- Button -->
					<div class="doc_btnWrap">
						<button type="button" class="btn btn-danger">취소</button>
						<button type="submit" class="btn btn-dark" id="saveButton">저장</button>
					</div>
				</div>
				</form>
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp"%>
		</div>
	</div>
	
<script type ="text/javascript">
/* 
$(document).ready(function() {
    // 에디터 스크립트 및 스타일을 로드한 후에 에디터 생성
    CKEDITOR.replace('editorTextarea', {
        // 에디터 설정 옵션을 추가
    	removePlugins: ['ImageUpload']
    });
    
    $("#saveButton").click(function(event) {
        event.preventDefault();

        var docTitle = $("#docTitleInput").val();
     	var docContent = CKEDITOR.instances.editorTextarea.getData();  // 에디터 인스턴스를 통해 데이터 가져오기

        // 유효성 검사
        if (!docTitle.trim()) {
            docTitle = "제목 없는 문서";
        }
        
        console.log(docTitle);
        console.log(docContent);

        $.ajax({
            url: '/tikitaka/document/add',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                docTitle: docTitle,
                docContent: docContent
            }),
            success: function(response) {
                alert('문서가 성공적으로 등록되었습니다.');
                window.location.href = '/tikitaka/document';  // 등록 후 문서 페이지로 리다이렉트
            },
            error: function(xhr, status, error) {
                alert('문서 등록 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });
}); */
// Toast UI Editor 초기화
const editor = new toastui.Editor({
	el: document.querySelector('#editor'),
	previewStyle: 'vertical',
	height: '550px',
	initialValue: '문서를 작성하세요.',     // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
	initialEditType: 'markdown'            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)

});

$("#saveButton").click(function(event) {
    event.preventDefault();

    var docTitle = $("#docTitleInput").val();
 	var docContent = editor.getMarkdown()

    // 유효성 검사
    if (!docTitle.trim()) {
        docTitle = "제목 없는 문서";
    }
    
    console.log(docTitle);
    console.log(docContent);

    $.ajax({
        url: '/tikitaka/document/add',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            docTitle: docTitle,
            docContent: docContent
        }),
        success: function(response) {
            alert('문서가 성공적으로 등록되었습니다.');
            window.location.href = '/tikitaka/document';  // 등록 후 문서 페이지로 리다이렉트
        },
        error: function(xhr, status, error) {
            alert('문서 등록 중 오류가 발생했습니다.');
            console.error(error);
        }
    });
});
</script>

</body>
