package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String userId;
	private String pwd;
	private String salt;
	private boolean status;
}
