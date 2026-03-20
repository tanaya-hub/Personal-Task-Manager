package com.controller;

import com.model.Board;
import com.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @GetMapping
    public List<Board> getBoards() {
        return boardService.getAllBoards();
    }
}