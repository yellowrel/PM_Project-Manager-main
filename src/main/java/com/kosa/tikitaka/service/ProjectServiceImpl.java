package com.kosa.tikitaka.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ChatRoomDTO;
import com.kosa.tikitaka.model.ChatUserDTO;
import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.model.UPMDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.utils.CodeMaker;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	private String inviteCode;

	@Override
	public List<ProjectDTO> getProjectList(String userId) {
		List<Integer> projNoList = sqlSession.selectList("UPMService.selectAll", userId);
		List<ProjectDTO> dtoList = new ArrayList<ProjectDTO>();
		for (int i = 0; i < projNoList.size(); i++) {
			int projNo = projNoList.get(i);
			dtoList.add(sqlSession.selectOne("projectDAO.selectByNo", projNo));
		}
		return dtoList;
	}

	@Override
	public List<ProjectDTO> searchListProject(String userId, String searchProject) {
		return sqlSession.selectList("projectDAO.searchList", searchProject);
	}

	@Override
	public void insertProject(UserDetailsImpl userDetails, ProjectDTO dto) {
		System.out.println("===> Mybatis insertProject() 호출");
		try {
			CodeMaker codeMaker = new CodeMaker();
			dto.setInviteCode(codeMaker.getCode());
			sqlSession.insert("projectDAO.insertProject", dto);
			int projNo = sqlSession.selectOne("projectDAO.selectCurrval");
			UPMDTO upmDTO = new UPMDTO();
			upmDTO.setRole(true);
			upmDTO.setProjNo(projNo);
			upmDTO.setUserId(userDetails.getUsername());
			sqlSession.insert("UPMService.insertUPM", upmDTO);
			ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
			chatRoomDTO.setChatRoomNo(projNo);
			chatRoomDTO.setChatRoomName(dto.getProjName());
			
			sqlSession.insert("chatService.insertChatRoom", chatRoomDTO);
			System.out.println(userDetails.getUsername());
			ChatUserDTO cuDTO = new ChatUserDTO(userDetails.getUsername(), null, projNo);
			
			sqlSession.insert("chatService.insertChatUser", cuDTO);

			sqlSession.insert("boardDAO.addBoard" , projNo);
			sqlSession.insert("boardDAO.addBoard" , projNo);
			sqlSession.insert("boardDAO.addBoard" , projNo);

			} catch (Exception e) { 
				sqlSession.rollback(); e.printStackTrace(); 
		}
	}

	@Override
	public ProjectDTO selectProject(int projNo) {
		return sqlSession.selectOne("projectDAO.selectByNo", projNo);
	}

	 @Override
	    public int deleteProject(ProjectDTO dto) {
	        System.out.println("===> Mybatis deleteBoard() 호출");
	        return sqlSession.delete("projectDAO.deleteProject" , dto);
	 }
	 
	 @Override
	 public int updateProject(ProjectDTO dto) {
		 int num = sqlSession.update("projectDAO.updateProject", dto);
		 System.out.println(num);
		 return num; 
	 }
	 
	 
	 
	@Override
	public ProjectDTO getProject(int projNo) {
		return sqlSession.selectOne("projectDAO.selectByProjNo", projNo);
	}

	@Override
	public void invitaionProject(ProjectDTO dto) {
		sqlSession.selectList("projectDAO.invitationProject", dto);
		
	}

}