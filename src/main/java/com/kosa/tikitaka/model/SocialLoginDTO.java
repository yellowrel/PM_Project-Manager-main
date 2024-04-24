package com.kosa.tikitaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialLoginDTO {
	private String username;
	private String nickname;
	private String socialId;
}
