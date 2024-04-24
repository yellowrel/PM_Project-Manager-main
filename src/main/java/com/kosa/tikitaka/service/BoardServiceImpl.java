package com.kosa.tikitaka.service;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.BoardDTO;
import com.kosa.tikitaka.model.NoteDTO;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private SqlSessionTemplate sqlSession;


    @Override
    public List<BoardDTO> getBoardList(int projNo) {
        System.out.println("===> Mybatis getBoardList() 호출");
        return sqlSession.selectList("boardDAO.getBoardList",projNo);
    }

    @Override
    public List<NoteDTO> getNoteList(int projNo) {
        System.out.println("===> Mybatis getNoteList() 호출");
        return sqlSession.selectList("noteDAO.getNoteList",projNo);
    }

    @Override
    public void updateBoard(int boardNo, String boardTitle) {
        System.out.println("===> Mybatis updateBoard() 호출");
        BoardDTO dto = new BoardDTO(boardNo,boardTitle,82);
        sqlSession.update("boardDAO.updateBoard", dto);
    }

    @Override
    public void addBoard(int projNo) {
        System.out.println("===> Mybatis addBoard() 호출");
        sqlSession.insert("boardDAO.addBoard" , projNo);
    }

    @Override
    public void deleteBoard(int boardNo) {
        System.out.println("===> Mybatis deleteBoard() 호출");
        sqlSession.delete("boardDAO.deleteBoard" , boardNo);
    }

    @Override
    public void addNote(int boardNo) {
        System.out.println("===> Mybatis addNote() 호출");
        BoardDTO dto = new BoardDTO(boardNo,"",82);
        sqlSession.insert("noteDAO.addNote" , dto);
    }

    @Override
    public void deleteNote(int noteNo) {
        System.out.println("===> Mybatis addNote() 호출");
        sqlSession.insert("noteDAO.deleteNote" , noteNo);
    }

    @Override
    public void updateNote(NoteDTO dto) {
        System.out.println("===> Mybatis updateNote() 호출");
        sqlSession.insert("noteDAO.updateNote" , dto);
    }

    @Override
    public void moveNoteRightBoard(NoteDTO dto) {
        System.out.println("===> Mybatis moveNoteRightBoard() 호출");
        sqlSession.update("noteDAO.moveNoteRightBoard",dto);
    }

    @Override
    public void moveNoteLeftBoard(NoteDTO dto) {
        System.out.println("===> Mybatis moveNoteLeftBoard() 호출");
        sqlSession.update("noteDAO.moveNoteLeftBoard",dto);
    }

    @Override
    public void moveNoteUptBoard(NoteDTO dto) {
        System.out.println("===> Mybatis moveNoteUpBoard() 호출");
        sqlSession.update("noteDAO.moveNoteUpBoard",dto);
    }

    @Override
    public void moveNoteDownBoard(NoteDTO dto) {
        System.out.println("===> Mybatis moveNoteUpBoard() 호출");
        sqlSession.update("noteDAO.moveNoteDownBoard",dto);

    }
}
