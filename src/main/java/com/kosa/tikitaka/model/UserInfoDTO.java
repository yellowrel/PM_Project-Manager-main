package com.kosa.tikitaka.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("userInfo")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
	private String userId;
	private String name;
	private boolean gender;
	private String email;
	private String tel;
	private String postCode;
	private String addr1;
	private String addr2;
	private Date birthday;
	private Date regDate;
	private int projOwnerNum;
}
