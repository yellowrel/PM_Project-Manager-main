package com.kosa.tikitaka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/motion")
public class MotionController {

	@RequestMapping(value = "/getproj", method = RequestMethod.GET)
	public String projectList() {
		return "project/projectList";
	}

	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String mainPage() {
		return "project/mainPage";
	}

	@RequestMapping(value ="/videoChat", method = RequestMethod.GET)
	public String board() {
		return "videoChat";
	}

	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public String document() {
		return "document";
	}
	
//	@RequestMapping(value = "/docAddPage", method = RequestMethod.GET)
//	public String docAddPage() {
//		return "docAddPage";
//	}

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String test() {
		return "sample/layoutSample";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/signUp")
	public String signUp() {
		return "signUp";
	}
}

