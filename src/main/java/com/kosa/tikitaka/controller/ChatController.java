package com.kosa.tikitaka.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.kosa.tikitaka.model.ChatDTO;
import com.kosa.tikitaka.model.MessageRequestDTO;
import com.kosa.tikitaka.model.MessageResponseDTO;
import com.kosa.tikitaka.service.ChatMessageService;
import com.kosa.tikitaka.service.ChatRoomJoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatMessageService chatMessageService;
	private final ChatRoomJoinService chatRoomJoinService;
	
	@MessageMapping("/chat/message")
	public void greeting(@RequestBody MessageRequestDTO message) {
		System.out.println(message);
		
		ChatDTO chat = chatMessageService.saveMessage(message);
		
		List<MessageResponseDTO> chatMsgList = new ArrayList<MessageResponseDTO>();
		
		if(chat.getMessageType().equals(message.getType())) {
			
			if(chatRoomJoinService.userEnterChk(message).isPresent()) {
				chatMsgList = chatMessageService.chatMessageList(message);
			} else {
				chatRoomJoinService.saveEnterTime(message);
				chatMsgList = chatMessageService.chatMessageList(message);
			}
		} else {
			MessageResponseDTO messageResponseDto = new MessageResponseDTO(chat);

			chatMsgList.add(messageResponseDto);
		}
		messagingTemplate.convertAndSend("/sub/chat/rooms/" + message.getRoomId(),chatMsgList);
	}
}
