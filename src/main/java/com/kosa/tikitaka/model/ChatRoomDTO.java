package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("chatRoom")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
	private int chatRoomNo;
	private String chatRoomName;
}
