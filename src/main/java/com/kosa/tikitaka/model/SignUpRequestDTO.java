package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("signUp")
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
	private UserDTO userDTO;
	private UserInfoDTO userInfoDTO;
}
