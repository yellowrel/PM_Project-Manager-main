package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("profile")
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	private String userId;
	private String uuid;
	private String imagePath;
	private String thumbnailPath;
}
