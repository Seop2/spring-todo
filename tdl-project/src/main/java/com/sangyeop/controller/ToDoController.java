package com.sangyeop.controller;

import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    UserRepository userRepository;

    User currentUser;

    @PostMapping("/list")
    public String list(@RequestBody HashMap payload) {
        if (currentUser==null) {
            currentUser =  userRepository.findById(payload.get("id").toString());
        }
        return "/todo/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        if (currentUser==null) {
            return "redirect:/login/form";
        }
        model.addAttribute("todoList", toDoRepository.findByUserOrderByIdx(currentUser));
        return "/todo/list";
    }

    @PostMapping
    public ResponseEntity<?> postToDo(@RequestBody ToDo toDo){
        toDo.regist();
        currentUser.add(toDo);
        toDoRepository.save(toDo);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

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
        persistTodo.edit(description);
        toDoRepository.save(persistTodo);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout(){
        currentUser = null;
        return "login/form";
    }
}
