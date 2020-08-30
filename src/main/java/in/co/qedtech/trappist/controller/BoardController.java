package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.Board;
import in.co.qedtech.trappist.model.BookTopic;
import in.co.qedtech.trappist.model.RevisionSlide;
import in.co.qedtech.trappist.model.School;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.UpdateBookTopicRequest;
import in.co.qedtech.trappist.repository.BoardRepository;

import in.co.qedtech.trappist.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired BoardRepository boardRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/boards")
    public List<Board> getBoards(){
        List<Board> boards = boardRepository.findAll();

        if(boards.size() == 0 || boards == null) {
            ArrayList<Board> boardList = new ArrayList<>();

            boardList.add(new Board("Central Board of Secondary Education", "CBSE"));
            boardList.add(new Board("The Indian Certificate of Secondary Education", "ICSE"));
            boardList.add(new Board("Board of Secondary Education, Madhya Pradesh", "MPBSE"));
            boardList.add(new Board("Board of Secondary Education, Chattisgarh", "CGBSE"));

            boardRepository.saveAll(boardList);
            boards = boardRepository.findAll();
        }

        return boards;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/save/board/{name:.+}+{alias:.+}")
    public ResponseEntity<?> saveBoard(@PathVariable String name, @PathVariable String alias) {
        if(boardRepository.existsByName(name)) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        if(boardRepository.existsByAka(alias)) {
            return new ResponseEntity(new ApiResponse(false, "Alias already exists"), HttpStatus.BAD_REQUEST);
        }

        Board board = new Board(name, alias);
        boardRepository.save(board);
        return new ResponseEntity(new ApiResponse(true, "Board created successfully"), HttpStatus.CREATED);
    }
}
