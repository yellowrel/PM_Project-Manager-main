<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>TIKITAKA</title>
<link href="${contextPath}/resources/css/reset.css" rel="stylesheet">
<link href="${contextPath}/resources/css/project.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
	integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
	integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script defer="defer" type="text/javascript"src="${contextPath}/resources/js/project.js"></script>
	<script>
		function createProj() {
			var new_window_width = 420;
			var new_window_height = 280;
			var positionX = (window.screen.width / 2) - (new_window_width / 2);
			var positionY = (window.screen.height / 2) - (new_window_height / 2);

			window.open(
					"projectAdd", // 주소
					"프로젝트 생성", // 제목
					"width=" + new_window_width + ", height=" + new_window_height + ", top=" + positionY + ", left=" + positionX + ",resizable=no, toolbar=no, menubar=no"
			);
		}
	</script>
</head>

<body>
	<div class="c_container">
		<div class="c_header">
			<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
				<div class="container-fluid">
					<img alt="logo" src="${contextPath}/resources/img/tikitaka.jpg"
						class="c_logo">
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav me-auto mb-2 mb-lg-0" id="c_projHeader">
							<a class="c_welcome">
								<h4>
									<c:out value="${userId}" />
									님 반갑습니다.
								</h4><span id="logout" class="material-symbols-outlined"
								onclick="alert('로그아웃')">logout</span>
							</a>
						</ul>
						<form class="d-flex" action="search" method="post">
							<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" id="c_projectSearch" name="c_projectSearch">
							<button class="btn btn-outline-light" type="submit"><span class="material-symbols-outlined">search</span></button>
						</form>
					</div>
				</div>
			</nav>
		</div>
		<div class="c_main">
			<div class="c_innerMain">
				<button type="button" class="btn btn-dark" data-bs-toggle="modal"
					data-bs-target="#exampleModal">프로젝트 입장</button>
				<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal2" onclick="createProj()">프로젝트 생성</button>
				<div class="modal fade" id="exampleModal" tabindex="-1"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<!-- <div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel"></h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>  -->
							<div class="modal-body">
								<form>
									<div class="mb-3">
										<label for="recipient-name" class="col-form-label">생성코드</label>
										<input type="text" class="form-control" id="recipient-name">
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">닫기</button>
								<button type="button" class="btn btn-dark">완료</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="c_cardContiner">
				<c:forEach var="item" items="${projectList}">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title" projno="${item.projNo}">${item.projName}</h5>
							<p class="card-text">${item.detailedDescription}</p>
							<button type="button" class="btn btn-dark"
								onclick="location.href='${contextPath}/project/${item.projNo}'">입장하기</button>
							<div class="container">
								<div class="dropdown">
									<button type="button" class="btn btn-dark dropdown-toggle"
										data-bs-toggle="dropdown"></button>
									<div class="dropdown-menu">
										<button type="button" class="dropdown-item"
											onclick="openUpdateModal(${item.projNo})">수정</button>
										<button type="button" class="dropdown-item"
											onclick="openDeleteModal(${item.projNo})">삭제</button>
									</div>
								</div>
							</div>

						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="c_footer">
			<p>Copyright &copy; 2023. JY. All rights reserved
		</div>
	</div>

	<!-- 모달 삭제 -->
	<div class="modal fade" id="deleteModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">삭제할 프로젝트명</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<label for="inputText2" class="form-label"> </label> <input
							type="text" style="text-align: center;" class="form-control"
							id="deleteProjName" placeholder="프로젝트명">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">취소</button>
					<button type="button" class="btn btn-dark"
						onclick="deleteBtnHandler()">확인</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 모달 수정 -->
	<div class="modal fade" id="updateModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">수정할 프로젝트명</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="text" style="text-align: center;" class="form-control"
						id="updateProjectName" placeholder="프로젝트명"> <input
						type="text" style="text-align: center;" class="form-control"
						id="updateProjectDetail" placeholder="상세설명">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">취소</button>
					<button type="button" class="btn btn-dark"
						onclick="updateBtnHandler()">확인</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
		//로그아웃
	var logout = document.getElementById("logout");
	logout.addEventListener("click", function() {
   			 var targetUrl = "http://localhost:8080/tikitaka/motion/login";
   			 window.location.href = targetUrl;
		})
                //프로젝트 입장모달 
           /*      $(".btn-modal").click(function () {
                    var data = $(this).data('id');
                    $("#contents.body-contents").val(data);
                    $("#text-contents.body-contents").html(data);
                });
                $('#testBtn').click(function (e) {
                    e.preventDefault();
                    $('#testModal').modal("show");
                }); */

                // 삭제모달의 확인버튼이 눌렸을때
                function deleteBtnHandler() {
                    let projNo = document.querySelector("#deleteModal").getAttribute("projno");
                    if (projNo === null) {
                        return;
                    }

                //삭제
                function deleteProject(projNo) {
                    if (confirm("해당 프로젝트를 삭제하시겠습니까?"))
                        $.ajax({
                            type: "post",
                            url: "../project/deleteProject/",
                            dataType: "json",
                            contentType: "application/json",
                            data : JSON.stringify({
                            	"projNo" : projNo
                            }),
                            success: function (data) {
                            	console.log(data)
                            	 alert("삭제되었습니다.");
                                $("#deleteModal").modal("hide");
                                location.href ="../project/list";
                           	},
                            error: function (xhr, status, error) {
                                console.error(error);
                                alert("삭제 중 오류가 발생했습니다.");
                            }
                        });
	                }


                    // 적은값하고 삭제를 하고싶은 프로젝트명이랑 같은지확인
                    const check = document.querySelector("#deleteProjName").value;
                    let origin = document.querySelector("h5[projno='" + projNo + "']").textContent.trim();
                    if (check !== origin) {
                        alert('프로젝트명이 일치하지 않습니다.');
                        return;
                    }
                    // 다르면 return

                    // 같으면 ajax요청을 보내는 deleteProject 함수를 호출하면

                    deleteProject(projNo);
                }

                //수정

                // 수정모달의 확인버튼이 눌렸을때
                function updateBtnHandler() {
                    var projNo = document.querySelector("#updateModal").getAttribute("projno");
					var projName = $('#updateProjectName').val();
					var detailedDescription = $('#updateProjectDetail').val();
                    //let origin = document.querySelector("h5[projno='" + projNo +"']").textContent.trim();
                    if (projNo === null) {
                        return;
                    }
                    if (projName === null){
                    	return;
                    }
                    if (detailedDescription === null){
                    	return;
                   }
                    console.log(projNo);
                    console.log(projName);
                    console.log(detailedDescription);
                    if (confirm("수정 하시겠습니까?"))
                        $.ajax({
                            method: "post",
                            url: "../project/updateProject",
                            dataType: "json", // JSON 데이터를 문자열로 변환하여 보냄
                            contentType: "application/json",
                            data : JSON.stringify({
                           	 "projNo": projNo,
                             "projName": projName,
                             "detailedDescription": detailedDescription
                         }) ,
                            success: function (data) {
                            	console.log(data)
                                if (data == "1") {
                                    alert("수정 되었습니다.");
                                    $("#updateModal").modal("hide");
                                    location.href = "../project/list";
                                    console.log(projNo);
                                } else {
                                    alert("프로젝트 상세설명 입력해주세요.");
                                    console.log(projNo);
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error(error);
                                console.log(projNo);
                                alert("프로젝트 상세설명 입력해주세요.");
                            }
                        });
                }

                let updateModal = null;
                let deleteModal = null;

                function openUpdateModal(projNo) {
                    if (updateModal === null) {
                        updateModal = new bootstrap.Modal("#updateModal");
                    }
                    document.querySelector("#updateModal").setAttribute("projno", projNo);
                    updateModal.show();
                }

                function openDeleteModal(projNo) {
                    if (deleteModal === null) {
                        deleteModal = new bootstrap.Modal("#deleteModal");
                    }
                    document.querySelector("#deleteModal").setAttribute("projno", projNo);
                    deleteModal.show();
                }


            </script>
</body>

</html>