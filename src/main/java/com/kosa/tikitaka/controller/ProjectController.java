package com.kosa.tikitaka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "/{projNo}", method = RequestMethod.GET)
	public String projcetPage(Model model, @PathVariable int projNo, @AuthenticationPrincipal UserDetailsImpl userDetails)	{
		model.addAttribute("projectNo",projNo);
		ProjectDTO projectDTO = projectService.getProject(projNo);
		userDetails.setProjectDTO(null);
		return "project/mainPage";
	}

	@RequestMapping(value = "/list")
	public String projectList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		String userId = userDetails.getUsername();
		List<ProjectDTO> dto = projectService.getProjectList(userId);
		System.out.println(dto.toString());
		model.addAttribute("projectList", dto);
		model.addAttribute("userId", userDetails.getUsername());
		
		return "project/projectList";
	}
	
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	@PostMapping("/updateProject")
	@ResponseBody
	public int updateProject(@RequestBody ProjectDTO data) {
		System.out.println(data);
		int result=0;
		result =projectService.updateProject(data);
		return  result;
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	@PostMapping("/deleteProject")
    @ResponseBody
	    public int deleteProject(@RequestBody ProjectDTO data){
		System.out.println(data);
	    int result=0;
	    result =projectService.deleteProject(data);
	    return  result;
	}

	
	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	public String insertProject(@RequestParam("projName") String projName,
							  @RequestParam("detailedDescription") String detailedDescription,
							  UserDetailsImpl userDetails) {

		ProjectDTO dto = new ProjectDTO(projName,detailedDescription);
		projectService.insertProject(userDetails, dto);
		return "/project/projectAdd";
	}

	@RequestMapping(value="projectAdd")
	public String projectAdd(){
		return "project/projectAdd";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchList(@AuthenticationPrincipal UserDetailsImpl userDetails, String c_projectSearch, Model model){
		String usrId = userDetails.getUsername();
		List<ProjectDTO> dto = projectService.searchListProject(usrId,c_projectSearch);
		model.addAttribute("projectList", dto);
		model.addAttribute("userId", userDetails.getUsername());
		System.out.println(model);
		return "project/projectList";
	}

}
