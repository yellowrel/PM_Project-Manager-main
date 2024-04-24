<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${contextPath}/resources/css/document.css" rel="stylesheet">
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<!-- Editor's Style -->
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<!-- TUI 에디터 JS CDN -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
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
					<button type="button" class="btn btn-dark" onclick="window.location.href=' document/add'">문서 생성하기</button>
				</div>
				<div class="doc_content">
					<div class="doc_side">
						<ul class="list-group">
							<c:forEach items="${docList}" var="doc">
								<li class="list-group-item" onclick="showDocDetails(${doc.docPageNo})">${doc.docTitle}</li>
							</c:forEach>
						</ul>
					</div>
					<div class="doc_main">
						<div class="doc_title">
							<h3 id="docTitle">${doc.docTitle}</h3>
							<h6 id="updateDate">${doc.updateDate}</h6>
						</div>
						<div id="viewer">
							<h5 id="docContent">${doc.docContent}</h5>
						</div>
						<div class="doc_btnWrap">
							<button type="button" class="btn btn-danger" id="deleteButton" onclick="deleteDoc(${doc.docPageNo})">삭제하기</button>
							<button type="button" class="btn btn-dark" id="editButton" onclick="updateDoc(${doc.docPageNo})">수정하기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp"%>
		</div>
	</div>
</body>

<script>


//list color
$(document).ready(function() {
    $(".list-group-item").click(function() {
        $(".list-group-item").removeClass("active");
        $(this).addClass("active");
    });
});

//수정 및 삭제 버튼을 처음에는 숨김
$("#deleteButton").hide();
$("#editButton").hide();

function showDocDetails(docPageNo){
    $.ajax({
        url: "document/" + docPageNo,
        type: "GET",
        success: function (response) {
        	console.log(response);
            $("#docTitle").text(response.docTitle);
            $("#updateDate").text(response.updateDate);
            $("#docContent").html(response.docContent);
            toastUI(response)
            
            // 수정, 삭제 버튼에 데이터 속성을 추가하여 docPageNo 값을 저장
            $("#deleteButton").attr("data-docPageNo", docPageNo).show(); 
            $("#editButton").attr("data-docPageNo", docPageNo).show();
            
            // 수정 버튼 클릭시 에 데이터 속성 추가
            $("#editButton").on("click", function () {
                updateDoc(docPageNo);
            });
        },
        error: function (xhr, status, error) {
            console.error('Error occurred while sending data:', error);
        }
    });


    function toastUI(response){
        try{
            //에디터 인스턴스를 생성하고 초기화 한다
            const viewer = new toastui.Editor.factory({
                el:document.querySelector('#viewer'), //에디터를 생성할 html 태그의 id를 지정
                initialValue: response.docContent,		//에디터가 나타날 때 표시할 문자열 html
                height: '600px',
                toolbarItems: [],
                viewer:true
            });
        }catch(e){
            console.error(e);
        }
    }
}

//삭제
function deleteDoc() {
    var docPageNo = $("#deleteButton").attr("data-docPageNo");
    if (!docPageNo) {
        console.error("docPageNo is undefined.");
        return;
    }

    if (!confirm("해당 문서를 삭제하시겠습니까?")) return false;
    console.log(docPageNo);
    $.ajax({
        url: "document/delete/" + docPageNo,
        type: "DELETE",
        success: function(data) {
            if (data.status === 'success') {
                alert("삭제되었습니다.");
                location.reload();
            }
        },
        error: function(xhr, status, error) {
            console.error(error);
            alert("삭제 중 오류가 발생했습니다.");
        }
    });
}

//수정하기 페이지로
function updateDoc(docPageNo) {
    window.location.href = '/tikitaka/document/edit/' + docPageNo;
}


</script>
