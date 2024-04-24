package com.kosa.tikitaka.service;

import java.util.List;

import com.kosa.tikitaka.model.BoardDTO;
import com.kosa.tikitaka.model.NoteDTO;

public interface BoardService {

    public List<BoardDTO> getBoardList(int projNo);

    public List<NoteDTO> getNoteList(int projNo);
    public void updateBoard(int boardNo, String boardTitle);
    public void addBoard(int projNo);
    public void deleteBoard(int boardNo);

    public void addNote(int boardNo);
    public void deleteNote(int noteNo);
    public void updateNote(NoteDTO dto);

    public void moveNoteRightBoard(NoteDTO dto);
    public void moveNoteLeftBoard(NoteDTO dto);

    public void moveNoteUptBoard(NoteDTO dto);

    public void moveNoteDownBoard(NoteDTO dto);
}
