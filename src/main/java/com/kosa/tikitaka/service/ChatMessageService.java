package com.kosa.tikitaka.service;

import java.util.List;

import com.kosa.tikitaka.model.ChatDTO;
import com.kosa.tikitaka.model.MessageRequestDTO;
import com.kosa.tikitaka.model.MessageResponseDTO;

public interface ChatMessageService {

	ChatDTO saveMessage(MessageRequestDTO message);

	List<MessageResponseDTO> chatMessageList(MessageRequestDTO message);

}
