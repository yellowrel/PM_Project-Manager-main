package com.kosa.tikitaka.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("schedule")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	private int scheduleNo;			//번호
	private String scheduleTitle; 	//제목, 내용
	private Date startDay;			//시작일
	private Date endDay;			//종료일
	private int projNo;				//프로젝트번호
}
