package com.kosa.tikitaka.service;

import java.util.Optional;

import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.UserDTO;

public interface ChatService {
	Optional<ChatUserDTO> selectByUserIdAndChatRoomNo(ChatUserDTO cuDTO);
	void insertByChatUser(ChatUserDTO chatUser);
	Optional<ChatUserDTO> selectByUserIdAndChatRoomNo(UserDTO userDTO, ChatRoomDTO chatRoom);
}
