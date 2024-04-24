package com.kosa.tikitaka.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("board")
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int boardNo;
	private String boardTitle;
	private int projNo;
}
