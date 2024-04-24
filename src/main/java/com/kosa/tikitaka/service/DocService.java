package com.kosa.tikitaka.service;

import java.util.List;
import com.kosa.tikitaka.model.DocPageDTO;

public interface DocService {
	
	//리스트
    public List<DocPageDTO> getAllDocs();
    //상세보기
    public DocPageDTO getDocByNO(int docPageNo);
    //추가
    public void addDoc(DocPageDTO doc);
    //수정
    public void updateDoc(DocPageDTO doc);
    //삭제
    public void deleteDoc(int docPageNo);
}
