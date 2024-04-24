package com.kosa.tikitaka.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.MessageRequestDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.utils.ChatUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomJoinServiceImpl implements ChatRoomJoinService {

	private final ChatUtils chatUtils;
	private final ChatService chatService;
	
	@Override
	public Optional<ChatUserDTO> userEnterChk(MessageRequestDTO message) {
		
		ChatRoomDTO chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
		
		UserDTO userDTO = chatUtils.selectOneUser(message.getSender());
		
		Optional<ChatUserDTO> dto = chatService.selectByUserIdAndChatRoomNo(userDTO, chatRoom);
		
		return dto;
	}
	
	@Override
	public void saveEnterTime(MessageRequestDTO message) {
        // 해당 방 가져오기
        ChatRoomDTO chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
        // 유저 가져오기
        UserDTO user = chatUtils.selectOneUser(message.getSender());
        // enterTime 저장
        message.setCreatedAt(chatUtils.getCurrentTime());

        ChatUserDTO chatUser = new ChatUserDTO(user.getUserId(), message.getCreatedAt(), chatRoom.getChatRoomNo());
        
        chatService.insertByChatUser(chatUser);
    }
}
