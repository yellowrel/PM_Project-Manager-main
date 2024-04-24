package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("chatUser")
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDTO {
	private String userId;
	private String enterTime;
	private int chatRoomNo;
}
