package com.kosa.tikitaka.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ChatDTO;
import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.MessageRequestDTO;
import com.kosa.tikitaka.model.MessageResponseDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.utils.ChatUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

	@Autowired
	private final SqlSessionTemplate sqlSession;
	private final ChatUtils chatUtils;
	
	// messageType = "TALK" 채팅메세지 저장하기
	@Override
    public ChatDTO saveMessage(MessageRequestDTO message) {

        // 채팅방 가져오기
        ChatRoomDTO chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
        // 유저 가져오기
        UserDTO chatUser = chatUtils.selectOneUser(message.getSender());
        // 메세지 전달 시간 저장
        message.setCreatedAt(chatUtils.getCurrentTime());
        // Message 저장 객체 만들기
        ChatDTO chatMessage = new ChatDTO(0L, message.getType(), message.getMessage(), message.getCreatedAt(),
        							chatRoom.getChatRoomNo(), chatUser.getUserId());
        // Message 객체 저장
        sqlSession.insert("chatService.insertMsg", chatMessage);
        
        return chatMessage;
    }
	
	@Override
	public List<MessageResponseDTO> chatMessageList(MessageRequestDTO message) {
        // 채팅방 가져오기
        ChatRoomDTO chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
        message.setType("TALK");
        List<ChatDTO> selectMessageList = sqlSession.selectList("chatService.selectByChatRoom", chatRoom);
        List<MessageResponseDTO> responseChatList = new ArrayList<>();

        for(ChatDTO chatMessage : selectMessageList){
            MessageResponseDTO messageRequestDto = new MessageResponseDTO(chatMessage);
            responseChatList.add(messageRequestDto);
        }
        return responseChatList;
    }
}
