package com.kosa.tikitaka.service;

import java.util.Optional;

import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.model.UserInfoDTO;

public interface UserService {
	public Optional<UserDTO> findByUsername(String username);
	public UserDTO findByUserId(String userId);
	public UserInfoDTO findUserInfoByUsername(String username);
	public boolean isCheckUser(String userId);
	public boolean register(UserDTO userDto, UserInfoDTO userInfoDTO);
	public boolean login(String userId, String pwd);
}
