package com.kosa.tikitaka.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Alias("note")
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
	private int noteNo;
	private String noteTitle;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDay;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDay;
	private String noteContent;
	private String charger;
	private int boardNo;
	private int projNo;

	public NoteDTO(int noteNo, String noteTitle, Date noteStartday, Date noteEndday, String noteCharger) {
		this.noteNo = noteNo;
		this.noteTitle = noteTitle;
		this.startDay = noteStartday;
		this.endDay = noteEndday;
		this.charger = noteCharger;
	}

	public NoteDTO(int noteNo, int projNo) {
		this.noteNo = noteNo;
		this.projNo = projNo;
	}
}
