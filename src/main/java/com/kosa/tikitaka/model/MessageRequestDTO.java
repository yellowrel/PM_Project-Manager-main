package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("msgRequest")
@NoArgsConstructor
public class MessageRequestDTO {
	private String sender;
    private String roomId;
    private String message;
    private String createdAt;
    private String type;
}
