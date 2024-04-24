<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<style type="text/css">
* {
	font-family: 'Nanum Gothic', sans-serif;
	font-family: 'Noto Sans KR', sans-serif;
}

.chat_container {
	display: grid;
	grid-template-rows: 1fr 60px;
	border-left-color: #212529;
    border-left-style: solid;
    border-left-width: thin;
    height: 100vh;
}

.msgBtn {
	width: 90%;
	height: 90%;
	border: #212529 solid thin;
	border-radius: 2em;
	margin: 0 5% 0 5%;
	padding: 0px 70px 0px 20px;
}

.send {
	width:40px;
	height: 40px;
	border-radius: 70%;
	color: #fff;
	background-color: #212529;
	position: absolute;
	right: 1.8%;
	bottom: 1.5%;
}

.send .material-symbols-outlined {
	text-align: center;
	margin: 7px 10px 10px 10px;
}

.userEnter {
	text-align: center;
	font-size: 12px;
	margin-top: 10px;
	margin-bottom: 10px;
}

.myChat, .yourChat {
	max-width: 80%;
}

.yourChat {
	display: grid;
	grid-template-columns: 50px 1fr;
	gap: 10px;
}

.yourChat .name {
	font-size: 12px;
}

.yourChat .chatMsg {
	border: solid 1px #212529;
	border-radius: 0.5em;
	background-color: #212529;
	padding: 5px 20px 5px 20px;
	color: #fff;
	width: fit-content;
	max-width: 100%;
}

.myChat {
	margin-left: auto;
	margin-right: 20px;
}

.myChat .chatMsg{
	border: solid 1px #212529;
	border-radius: 0.5em;
	background-color: #fff;
	padding: 5px 20px 5px 20px;
	color: #212529;
	width: fit-content;
	max-width: 100%;
	margin-left: auto;
}

.chatProfile {
	width: 40px;
	height: 40px;
	border-radius: 2em;
	margin-left: 10px;
}

.noRead {
	position: absolute;
	font-size: 8px;
	right: 220px;
	top: 517px;
}

</style>
</head>
<body onload="javascript:connect()">
	<div class="chat_container" id="chatContainer">
		<div class="chat_main">
			<%-- <div class="userEnter">-----'이재진'님이 입장하셨습니다.-----</div>
			<div class="userEnter">-----'정준열'님이 입장하셨습니다.-----</div>
			<div class="myChat">
				<p class="chatMsg">안녕하세요.
			</div>
			<div class="myChat">
				<p class="chatMsg">반갑습니다.
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<div>
					<div class="name">정준열</div>
					<div><p class="chatMsg">안녕하세요!</div>
				</div>
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<div>
					<div class="name">정준열</div>
					<div><p class="chatMsg">안녕하세요!</div>
				</div>
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<p class="chatMsg">우리 프로젝트 같이 한번 잘해봐요!!!
			</div>
			<div class="myChat">
				<p class="chatMsg">좋습니다!
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<p class="chatMsg">팀명은 뭐로 지을까요?
			</div>
			<div class="myChat">
				<p class="chatMsg">불4조 어때요?
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<p class="chatMsg">굳굳!!!!!
			</div> --%>
			<%-- <div class="userEnter">-----'정준열'님이 입장하셨습니다.-----</div>
			<div class="userEnter">-----'이재진'님이 입장하셨습니다.-----</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<div>
					<div class="name">정준열</div>
					<div><p class="chatMsg">재진재진</div>
				</div>
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<div>
					<div class="name">정준열</div>
					<div><p class="chatMsg">비상비상</div>
				</div>
			</div>
			<div class="myChat">
				<p class="chatMsg">왜 무슨일인데
			</div>
			<div class="yourChat">
				<img alt="profile" src="${contextPath}/resources/img/chat.jpg" class="chatProfile">
				<div>
					<div class="name">정준열</div>
					<div><p class="chatMsg">코드에 문제가 있어 좀 알려줘</div>
				</div>
			</div>
			<div class="myChat">
				<p class="chatMsg">화상채팅 123으로 들어와
			</div> --%>
			<div class="userEnter">-----'admin'님이 입장하셨습니다.-----</div>
		</div>
		<form action="" class="chat_input" id="chat_input">
			<input type="text" class="msgBtn" placeholder="메세지를 입력하세요.">
			<input style="display: none">
			<label class="send"><span class="material-symbols-outlined">send</span></label>
		</form>
	</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js" integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script defer src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
function connect() {
	var client = Stomp.over('ws://localhost:8080/tikitaka/ws-stomp');
	client.connect({}, onConnect, onError);
};

function onConnect() {
	console.log('이거가 돌아가야해');
	/* document.addEventListener('DOMContentLoaded', function() {
	    // ...
	    
	    var messageInput = document.querySelector('.msgBtn');
		var sendButton = document.querySelector('.send');
	
	    messageInput.addEventListener('keypress', function(event) {
	        if (event.key === 'Enter') {
	            onMessage();
	        }
	    });
	
	    sendButton.addEventListener('click', function() {
	        onMessage();
		});
	}); */
	
	function onMessage() {
		var messageInput = document.querySelector('.msgBtn');
		var message = messageInput.value;
	
		if(message.trim() !== '') {
			client.send('/pub/chat/message',
				{},
				JSON.stringify({
					'sender': 'jaejin',
					'roomId': '1',
					'message': message,
					'createdAt': '',
					'type': 'TALK'
				})
			);
			message.value = '';
		}
	}
}

function onError() {
	console.log('에러발생');
}
</script>