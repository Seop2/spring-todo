package com.sangyeop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangyeop.domain.Comment;
import com.sangyeop.domain.CommentDTO;
import com.sangyeop.domain.ToDo;
import com.sangyeop.repository.CommentRepository;
import com.sangyeop.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author hagome
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/{idx}")
    public ResponseEntity<? > commentPost(@PathVariable Long idx, @RequestBody Comment comment){
        ToDo toDo = toDoRepository.findByIdx(idx);
        toDo.add(comment);
        comment.createComment();
        Comment savedComment = commentRepository.save(comment);
        CommentDTO commentDTO = new CommentDTO(savedComment);
        return new ResponseEntity<>(commentDTO , HttpStatus.CREATED);
    }

}
