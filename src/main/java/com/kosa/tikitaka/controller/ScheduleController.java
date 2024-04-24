package com.kosa.tikitaka.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.tikitaka.model.ScheduleDTO;
import com.kosa.tikitaka.service.ScheduleService;

@Controller
@RequestMapping("/")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	// 일정 조회
	@RequestMapping("/schedule")
	public String data(Model model) {

		model.addAttribute("list", scheduleService.calendarList());
		return "schedule";
	}

	// 일정 등록
	@RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap addSchedule(@RequestBody ScheduleDTO scheduleDTO) throws Exception {
		System.out.println("데이터: " + scheduleDTO);

		scheduleService.addSchedule(scheduleDTO);

		HashMap map = new HashMap<>();
		map.put("status", "success");
		map.put("list", scheduleService.calendarList());
		System.out.println("리스트:" + scheduleService.calendarList() + "\n");
		return map;
	}

	//일정 수정
	@RequestMapping(value = "/schedule/edit", method = RequestMethod.POST)
	@ResponseBody
	public HashMap updateSchedule(@RequestBody ScheduleDTO scheduleDTO) throws Exception {

		System.out.println("데이터: " + scheduleDTO);

		scheduleService.updateSchedule(scheduleDTO);

		HashMap map = new HashMap<>();
		map.put("status", "success");
		map.put("list", scheduleService.calendarList());
		System.out.println("list: " + scheduleService.calendarList());
		return map;
	}

	//일정 삭제
	@RequestMapping(value = "/schedule/deletion/{scheduleNo}", method = RequestMethod.DELETE)
	@ResponseBody
	public HashMap deleteSchedule(@PathVariable("scheduleNo") int scheduleNo) {
		System.out.println("delete schedule : " + scheduleNo);
		scheduleService.deleteSchedule(scheduleNo);
		HashMap map = new HashMap<>();
		map.put("status", "success");
		return map;
	}
}
