package com.service;

import com.model.Board;
import com.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
}