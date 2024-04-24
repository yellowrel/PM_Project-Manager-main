<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<html>
<head>
<title>일정</title>
<style type="text/css">
.layout_container {
	display: grid;
	grid-template-columns: 9fr 3fr;
	height: 100vh;
}

.layout_body {
	display: grid;
	grid-template-rows: 70px 10fr;
}
</style>
<link href="${contextPath}/resources/css/schedule.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

<!-- googlecalendar -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- fullcalendar -->
<link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.css' rel='stylesheet' />
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.js'></script>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/locales-all.min.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
</head>

<body>
	<div class="layout_container">
		<div class="layout_body">
			<div class="layout_header">
				<%@ include file="/WEB-INF/views/fixed/header.jsp"%>
			</div>
			<div class="layout_main">
				<div class="title-section">
					<div class="addsection">
						<button type="button" class="btn btn-outline-secondary addsch" data-bs-toggle="modal" data-bs-target="#calendarModal">일정 추가</button>
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="calendarModal" tabindex="-1" aria-labelledby="calendarModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header">
								<h1 class="modal-title fs-5" id="calendarModalLabel">일정을 입력하세요.</h1>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form method="post">
									<input type="date" name="startDay" id="startDay"> 
								  - <input type="date" name="endDay" id="endDay"> 
								    <input type="hidden" name="scheduleNo" id="scheduleNo">
										<div class="mb-3">
											<label for="message-text" class="col-form-label">Title</label>
											<textarea class="form-control" id="message-text" name="scheduleTitle"></textarea>
										</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" id="addbtn" onclick="save()">저장</button>
								<button type="button" class="btn btn-secondary d-none" id="delbtn">삭제</button>
								<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal" onclick="cancelBtn()">닫기</button>
							</div>
						</div>
					</div>
				</div>
				<!-- calendar 태그 -->
				<div id='calendar-container' class='calendar-container'>
					<div id='calendar'></div>
				</div>
			</div>
		</div>
		<div class="layout_side">
			<%@ include file="/WEB-INF/views/fixed/side.jsp"%>
		</div>
	</div>
</body>
<script>

// 모달 창 닫을 떄마다 입력 필드 초기화
$('#calendarModal').on('hidden.bs.modal', function (e) {
    $("#message-text").val("");
    $("#startDay").val("");
    $("#endDay").val("");
});
//추가
function save() { 
    var startDate = $("input[type='date']:first").val(); 
    var endDate = $("input[type='date']:last").val(); 
    var title = $("#message-text").val();

    var scheduleDTO = {
        startDay: startDate,
        endDay: endDate,
        scheduleTitle: title
    };

    $.ajax({
        url: "schedule/add",
        type: "POST",
        contentType:"application/json",
        data: JSON.stringify(scheduleDTO),
        success: function(response) {

            if(response.status === 'success') {
                alert("일정이 추가되었습니다.");
                location.reload(); // 페이지 새로고침 (캘린더 업데이트)
            } else {
                alert("일정 추가에 실패했습니다.");
            }
            $('#calendarModal').modal('hide'); // 모달 닫기
        }
     });
}

//수정
function update() {
    var formData = {};
    formData.scheduleNo = $("#scheduleNo").val();
    formData.scheduleTitle = $("#message-text").val();
    formData.startDay = $("input[type='date']:first").val();
    formData.endDay = $("input[type='date']:last").val();


    $.ajax({
        url: "schedule/edit", 	// 요청을 보낼 URL
        type: "POST", 			// HTTP 메서드 (GET, POST, PUT, DELETE 등)
        data: JSON.stringify(formData),
        contentType: "application/json",
        dataType: "json", 		// 응답 데이터 타입 (json, xml, text 등)
        success: function(response) {
            // 요청이 성공했을 때의 처리 로직
            console.log(response); // 응답 데이터 출력
            alert("수정이 완료되었습니다.");
            $("#calendarModal").modal("hide");
            location.href="/tikitaka/schedule";
        },
        error: function(xhr, status, error) {
            // 요청이 실패했을 때의 처리 로직
            console.error(error); // 에러 메시지 출력
        }
    });
}

//삭제
function del(scheduleNo) {
	 if(!confirm("해당 일정을 삭제하시겠습니까?")) return false;
	console.log(scheduleNo);
    $.ajax({
        url: "schedule/deletion/" + scheduleNo,
        type: "DELETE",
        success: function (response) {
            alert("삭제되었습니다.");
            $("#calendarModal").modal("hide");
            location.href = "/tikitaka/schedule";
        },
        error: function (xhr, status, error) {
            console.error(error);
            alert("삭제 중 오류가 발생했습니다.");
        }
    });
}

//calendar element 취득
var calendarEl = document.getElementById('calendar');

var calendar = new FullCalendar.Calendar(calendarEl, {
	googleCalendarApiKey: 'AIzaSyC1Oqrjs44yB-QREgK7VQ9paY4ISx2o5T8', //googleCalendar
	height : '100%', 		// calendar 높이 설정
	expandRows : true, 		// 화면에 맞게 높이 재설정
	slotMinTime : '00:00',  // Day 캘린더에서 시작 시간
	slotMaxTime : '23:59',  // Day 캘린더에서 종료 시간
	displayEventTime: false,// 시간 표시 안함
	// 해더에 표시할 툴바
	headerToolbar : {
		left : 'prev,next today',
		center : 'title',
		right : 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
	},
	
	initialView : 'dayGridMonth', // 초기 로드될 때 보이는 캘린더 화면 (기본 설정: Month)
 // initialDate: '2023-07-01', 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
	navLinks : true, 	// 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크
	editable: true, 	// 수정 가능
	selectable: true, 	// 달력 일자 드래그 설정가능
	nowIndicator : true, // 현재 시간 마크
	dayMaxEvents : true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
	locale : 'ko',		 // 한국어 설정
	eventAdd : function(obj) { // 이벤트가 추가되면 발생하는 이벤트
		console.log(obj);
	},
	eventChange : function(obj) { // 이벤트가 수정되면 발생하는 이벤트
		console.log(obj);
	},
	eventRemove : function(obj) { // 이벤트가 삭제되면 발생하는 이벤트
		console.log(obj);
	},

	/* 이벤트 추가 */
	dateClick: function(info) { 	// 캘린더 날짜 클릭시 이벤트 생성
		  	$("#addbtn").show(); 	// '저장' 버튼 표시
		    $("#delbtn").addClass('d-none'); // '삭제' 버튼 숨기기
		    $("#addbtn").removeAttr("onclick");	// 온클릭 속성 삭제
		    $("#addbtn").text("저장");
		    $("#addbtn").attr("onclick", "save();"); // '저장' 온클릭 속성 부여
			$("#calendarModal").modal("show"); // modal 나타내기
		},
	/* 이벤트 수정, 삭제 */
	eventClick: function (info) { 			//이벤트 클릭시 모달창 나타내기
		$("#addbtn").removeAttr("onclick");	// 온클릭 속성을 삭제
		$("#addbtn").text("수정");
		$("#addbtn").attr("onclick", "update();"); // 온클릭 속성을 다시부여

	var eventObj = info.event;  // 클릭된 이벤트 정보 가져오기
		$("#updatebtn").show(); // '수정' 버튼 표시
		$("#delbtn").removeClass('d-none');  // '삭제' 버튼 표시 (CSS 클래스 'd-none' 제거)

		//수정 모달창에 값 불러오기
		$("#message-text").val(eventObj.title);
		$("#scheduleNo").val(eventObj.id);
 		$("input[type='date']:first").val(moment(eventObj.start).format('YYYY-MM-DD'));

 		if (eventObj.end) {
            $("input[type='date']:last").val(moment(eventObj.end-1).format('YYYY-MM-DD'));
        } else {
            $("input[type='date']:last").val(moment(eventObj.start).format('YYYY-MM-DD'));
        }
	 	// 추가할 데이터 여기에 작성

	 	// 수정 버튼이 클릭되었을 때의 동작 정의
	     $("#addbtn").off().on("click", function() {
		        eventObj.setProp('title', $("#message-text").val());
		        if ($("#startDay").val()) {
		            var newStart = new Date($("#startDay").val());
		            if ($("#endDay").val()) {
		                var newEnd = new Date($("#endDay").val());
		                eventObj.setDates(newStart, newEnd);
		            } else {
		              eventObj.setDates(newStart);
		            }
		        }
		        $('#calendarModal').modal('hide');
		     });

	  // "삭제" 버튼 클릭 시 동작 정의
	     $("#delbtn").on("click", function() {
	         var scheduleNo = $("#scheduleNo").val(); // 삭제할 이벤트의 scheduleNo 가져오기
	         if (scheduleNo) {
	             // scheduleNo 값이 유효한 경우에만 삭제 요청 보내기
	             del(scheduleNo);
	         } else {
	             // scheduleNo 값이 없는 경우에 대한 alert
	             alert("삭제할 이벤트를 선택해주세요.");
	         }
	     });
		    $("#calendarModal").modal("show");  // 모달 나타내기
		},
		
		//drag & drop으로 일정 수정
		eventDrop: function(info) {
	        var start = info.event.start;
	        var end = info.event.end - 1;
	        var eventData = {
	            scheduleNo: info.event.id,
	            scheduleTitle: info.event.title,
	            startDay: moment(start).format('YYYY-MM-DD'),
	            endDay: end ? moment(end).format('YYYY-MM-DD') : moment(start).format('YYYY-MM-DD')
	        };

	        $.ajax({
	            url: "schedule/edit",
	            type: "POST",
	            contentType:"application/json",
	            data: JSON.stringify(eventData),
	            success: function(response) {
	                if(response.status === 'success') {
	                    alert("일정이 수정되었습니다.");
	                    location.reload(); 
	                } else {
	                    alert("일정 수정에 실패했습니다.");
	                }
	             }
	         });
	    },

	eventDidMount:function(info) {
		console.log();
	},
	//이벤트 (일정 출력)
	events: [
	    <c:forEach var="scheduleList" items="${list}">
	    {
	    	color:'cfe2f3',
	        textColor:'808080',
	        id : '${scheduleList.scheduleNo}',
			title: '${scheduleList.scheduleTitle}',
			start: '<fmt:formatDate value="${scheduleList.startDay}" pattern="yyyy-MM-dd" />',
			end: '<fmt:formatDate value="${scheduleList.endDay}" pattern="yyyy-MM-dd" />',
	    } ,
	    </c:forEach>
	 ],
	    //google calendar 연동
 	eventSources:[
	{
		googleCalendarId : 'ko.south_korea#holiday@group.v.calendar.google.com',
		color : 'ffe4e1',
		textColor : 'ff0000'
	}
	], 
});

//캘린더 랜더링
calendar.render();
</script>
</html>