package com.kosa.tikitaka.service;

import java.util.Optional;

import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.MessageRequestDTO;

public interface ChatRoomJoinService {
	Optional<ChatUserDTO> userEnterChk(MessageRequestDTO message);
	void saveEnterTime(MessageRequestDTO message);

}
