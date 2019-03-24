package com.sangyeop.controller;

import com.sangyeop.domain.ToDo;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/todos" , produces= MediaType.APPLICATION_JSON_VALUE)
public class ToDoRestController {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> postToDo(@RequestBody ToDo toDo){
        toDo.regist();
        toDo.setUser(userRepository.findById("hagome"));
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
}
