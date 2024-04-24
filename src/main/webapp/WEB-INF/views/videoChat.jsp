<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<html>

<head>
    <title>화상 채팅</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/videoChat.css">
    <style type="text/css">
        .layout_container {
            display: grid;
            grid-template-columns: 9fr 3fr;
            height: 100vh;
        }

        .layout_body {
            display: grid;
            grid-template-rows:70px 10fr;
        }

        .layout_main{
            display: grid;
            grid-template-rows:70px 1fr;
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
            <div>
                <p id="notification" hidden></p>
                <div class="entry-modal" id="entry-modal">
                    <i class="fs-3 material-symbols-outlined align-middle">videocam</i>
                    <span class="fw-bolder">화상 채팅</span>
                    <input id="room-input" class="room-input" placeholder="ID를 입력해 주세요.">
                    <div>
                        <button class="btn btn-outline-secondary addsch" onclick="createRoom()">만들기</button>
                        <button class="btn btn-outline-secondary addsch" onclick="joinRoom()">들어가기</button>
                    </div>
                </div>
            </div>
            <div class="meet-area">
                <!-- Remote Video Element-->
                <video id="remote-video"></video>

                <!-- Local Video Element-->
                <video id="local-video" hidden="hidden"></video>
                <div class="meet-controls-bar">
                    <button onclick="startScreenShare()">화면 공유</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://unpkg.com/peerjs@1.3.1/dist/peerjs.min.js"></script>
    <script src="${contextPath}/resources/js/videoChat.js"></script>
    <div class="layout_side">
        <%@ include file="/WEB-INF/views/fixed/side.jsp" %>
    </div>
</div>
</body>



</html>