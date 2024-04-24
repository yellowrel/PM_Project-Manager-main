package com.kosa.tikitaka.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("#ChatHandler, handleMessage");
		log.info(session.getId() + ": " + message);
		
		String payload = message.getPayload();
		log.info("payload =>" + payload);
		
		TextMessage textMessage = new TextMessage("Welcome chatting server!");
		session.sendMessage(textMessage);
	}
}
