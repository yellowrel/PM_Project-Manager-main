package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("project")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
	private int projNo;
	private String projName;
	private String inviteCode;
	private String detailedDescription;


	public ProjectDTO(String projName, String detailedDescription) {
		this.projName = projName;
		this.detailedDescription = detailedDescription;
	}
}
