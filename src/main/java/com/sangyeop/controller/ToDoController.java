package com.sangyeop.controller;

import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author hagome
 * @since  2019-03-29
 */
@Controller
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    UserRepository userRepository;

    /* @AuthenticationPrincipal SecurityUser securityUser : 현재 로그인한 User 가져오기 */
    @GetMapping("/list")
    public String list(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User sessionUser) {
        User user = userRepository.findById(sessionUser.getUsername());
        model.addAttribute("todoList", toDoRepository.findByUserOrderByIdx(user));
        return "/todo/list";
    }

    /* 게시물 등록 */
    @PostMapping
    public ResponseEntity<?> postToDo(@RequestBody ToDo toDo, @AuthenticationPrincipal org.springframework.security.core.userdetails.User sessionUser){
        User user = userRepository.findById(sessionUser.getUsername());
        toDo.regist();
        user.add(toDo);
        toDoRepository.save(toDo);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    /* 게시물 삭제 */
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteToDo(@PathVariable("idx") Long idx) {
        toDoRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putToDo(@PathVariable("idx") Long idx) {
        ToDo persistTodo = toDoRepository.getOne(idx);
        persistTodo.update();
        toDoRepository.save(persistTodo);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/edit/{idx}")
    public ResponseEntity<?> putDescription(@PathVariable("idx") Long idx, @RequestBody ToDo toDo) {
        String description = toDo.getDescription();
        ToDo persistTodo = toDoRepository.getOne(idx);
        persistTodo.switchStatus(description);
        toDoRepository.save(persistTodo);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}