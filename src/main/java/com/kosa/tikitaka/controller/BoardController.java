package com.kosa.tikitaka.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosa.tikitaka.model.BoardDTO;
import com.kosa.tikitaka.model.NoteDTO;
import com.kosa.tikitaka.service.BoardService;

@Controller
@RequestMapping("/")
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping("board")
    public String board(Model model) {
        model.addAttribute("noteList",boardService.getNoteList(82));
        model.addAttribute("boardList",boardService.getBoardList(82));
        return "board";
    }

    @RequestMapping("board/update")
    public String updateBoard(BoardDTO dto) {
        return "boardUpdate";
    }

    @RequestMapping(value="board/updateConfirm", method = RequestMethod.POST)
    public String updateBoardConfirm(int boardNo, String boardTitle) {
        boardService.updateBoard(boardNo, boardTitle);
        return "boardUpdate";
    }

    @RequestMapping(value = "board/add")
    public String addBoard(){
        boardService.addBoard(82);
        return "redirect:/board";
    }

    @RequestMapping(value = "board/delete", method = RequestMethod.POST)
    public String deleteBoard(int boardNo){
        boardService.deleteBoard(boardNo);
        return "redirect:/board";
    }

    @RequestMapping(value = "note/add", method = RequestMethod.POST)
    public String addNote(int boardNo){
        boardService.addNote(boardNo);
        return "redirect:/board";
    }

    @RequestMapping(value ="note/delete", method = RequestMethod.POST)
    public String deleteNote(int noteNo){
        boardService.deleteNote(noteNo);
        return "redirect:/board";
    }

    @RequestMapping("note/update")
    public String updatenote(int noteNo) {
        return "noteUpdate";
    }

    @RequestMapping(value="note/updateConfirm", method = RequestMethod.POST)
    public String updateNoteConfirm(@RequestParam("noteNo") int noteNo,
                                    @RequestParam("noteTitle") String noteTitle,
                                    @RequestParam("noteStartday") @DateTimeFormat(pattern="yyyy-MM-dd")Date noteStartday,
                                    @RequestParam("noteEndday") @DateTimeFormat(pattern="yyyy-MM-dd")Date noteEndday,
                                    @RequestParam("noteCharger") String noteCharger) {
        NoteDTO dto = new NoteDTO(noteNo, noteTitle, noteStartday, noteEndday, noteCharger);
        //
        boardService.updateNote(dto);
        return "noteUpdate";
    }

    @RequestMapping(value="noteMoveBoard",  method = RequestMethod.GET) //보드 좌우로 노트가 이동 보드마지막으로 끝이면 예외처리
    public String moveNoteBoard(@RequestParam int noteNo, @RequestParam int dir, @RequestParam int projNo ){
        NoteDTO dto = new NoteDTO(noteNo,projNo);

        if(dir==1){
            boardService.moveNoteLeftBoard(dto);
        }else if(dir==2){
            boardService.moveNoteRightBoard(dto);
        }
        return "redirect:/board";
    }

    @RequestMapping(value="moveNote", method = RequestMethod.GET) //노트 번호 정렬한후 rownum 바로아래인애랑 교환 끝이면 예외처리
    public String moveNote(@RequestParam int noteNo, @RequestParam int dir, @RequestParam int projNo){
        NoteDTO dto = new NoteDTO(noteNo,projNo);
        System.out.println(dto);
        System.out.println(dir);
        if(dir==1){
            boardService.moveNoteUptBoard(dto);
        }else if(dir==2){
            boardService.moveNoteDownBoard(dto);
        }
        return "redirect:/board";
    }

}