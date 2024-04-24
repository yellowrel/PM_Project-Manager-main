package com.kosa.tikitaka.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatUtils {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public ChatRoomDTO selectOneChatRoom(String roomId) {
		int chatRoomNo = Integer.parseInt(roomId);
		ChatRoomDTO dto = sqlSession.selectOne("chatService.selectById", chatRoomNo);
		
		if(dto == null) {
			throw new NullPointerException("해당 채팅방이 존재하지 않습니다.");
		}
		
		return dto; 
	}
	
	public UserDTO selectOneUser(String userId) {
		UserDTO dto = sqlSession.selectOne("userService.selectById", userId);
		
		if(dto == null) {
			throw new NullPointerException("해당 회원이 존재하지 않습니다.");
		}
		
		return dto;
	}
	
	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		return sdf.format(date);
	}
}
