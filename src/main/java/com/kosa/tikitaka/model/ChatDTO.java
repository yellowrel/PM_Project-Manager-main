package com.kosa.tikitaka.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("chat")
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
	private long chatNo;
	private String messageType;
	private String message;
	private String createdAt;
	private int chatRoomNo;
	private String userId;
	
	public enum MessageType {
		ENTER, TALK
	}
}