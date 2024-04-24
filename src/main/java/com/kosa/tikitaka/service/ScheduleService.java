package com.kosa.tikitaka.service;

import java.util.List;

import com.kosa.tikitaka.model.ScheduleDTO;

public interface ScheduleService {

	//리스트
	public List<ScheduleDTO> calendarList();
	//추가
	public void addSchedule(ScheduleDTO scheduleDTO);
	//수정
	public int updateSchedule(ScheduleDTO scheduleDTO);
	//삭제
	public int deleteSchedule(int scheduleNo);
}
