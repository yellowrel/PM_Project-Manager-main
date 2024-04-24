package com.kosa.tikitaka.service;

import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.UserDTO;

@Service
public class ChatServiceImple implements ChatService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Optional<ChatUserDTO> selectByUserIdAndChatRoomNo(ChatUserDTO cuDTO) {
		ChatUserDTO dto = sqlSession.selectOne("chatService.selectChatUser", cuDTO); 
		return Optional.ofNullable(dto);
	}

	@Override
	public void insertByChatUser(ChatUserDTO chatUser) {
		sqlSession.insert("chatService.inserChatUser", chatUser);
	}

	@Override
	public Optional<ChatUserDTO> selectByUserIdAndChatRoomNo(UserDTO userDTO, ChatRoomDTO chatRoom) {
		ChatUserDTO cDTO = new ChatUserDTO();
		cDTO.setUserId(userDTO.getUserId());
		cDTO.setChatRoomNo(chatRoom.getChatRoomNo());
		
		ChatUserDTO dto = sqlSession.selectOne("chatService.selectByUserIdAndChatRoom", cDTO);
		
		return Optional.ofNullable(dto);
	}
}
