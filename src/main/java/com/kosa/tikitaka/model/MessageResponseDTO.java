package com.kosa.tikitaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
	private Long messageId;
    private String userId;
    private int chatRoomNo;
    private String message;
    private String createdAt;
    private String type;
    
    public MessageResponseDTO(ChatDTO chatDTO) {
    	this.messageId = chatDTO.getChatNo();
    	this.userId = chatDTO.getUserId();
    	this.chatRoomNo = chatDTO.getChatRoomNo();
    	this.message = chatDTO.getMessage();
    	this.createdAt = chatDTO.getCreatedAt();
    	this.type = chatDTO.getMessageType();
    }
}
