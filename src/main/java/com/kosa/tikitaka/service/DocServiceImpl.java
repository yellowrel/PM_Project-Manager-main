package com.kosa.tikitaka.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kosa.tikitaka.model.DocPageDTO;

@Service
public class DocServiceImpl implements DocService {
	 
	@Autowired
	    private SqlSessionTemplate sqlSession;

	@Override
	public List<DocPageDTO> getAllDocs() {
		System.out.println("===> Mybatis getAllDocs() 호출");
		return sqlSession.selectList("DocDAO.getAllDocs");
	}

	@Override
	public DocPageDTO getDocByNO(int docPageNo) {
		System.out.println("===> Mybatis getDocByNO() 호출");
		try {
			sqlSession.selectOne("DocDAO.getDocByNO" , docPageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sqlSession.selectOne("DocDAO.getDocByNO" , docPageNo);
	}
	
	@Override
	public void addDoc(DocPageDTO docDTO) {
		System.out.println("===> Mybatis addDoc() 호출");
		sqlSession.insert("DocDAO.addDoc", docDTO);
	}

	@Override
	public void updateDoc(DocPageDTO docDTO) {
		System.out.println("===> Mybatis updateDoc() 호출");
		sqlSession.update("DocDAO.updateDoc", docDTO);
	}

	@Override
	public void deleteDoc(int docPageNo) {
		System.out.println("===> Mybatis deleteDoc() 호출");
		sqlSession.delete("DocDAO.deleteDoc", docPageNo);
	}
}
