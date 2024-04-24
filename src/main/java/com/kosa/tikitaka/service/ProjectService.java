package com.kosa.tikitaka.service;

import java.util.List;

import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;

public interface ProjectService {
	public List<ProjectDTO> getProjectList(String userId);
	public ProjectDTO selectProject(int projNo);
	public void insertProject(UserDetailsImpl userDetails, ProjectDTO dto);
	public List<ProjectDTO> searchListProject(String userId, String searchProject);
	public int deleteProject(ProjectDTO dto);
	public ProjectDTO getProject(int projNo);
	int updateProject(ProjectDTO dto);






	public void invitaionProject(ProjectDTO dto);
}
