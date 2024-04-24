package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("upm")
@NoArgsConstructor
@AllArgsConstructor
public class UPMDTO {
	private int upmNo;
	private boolean role;
	private String userId;
	private int projNo;
}
