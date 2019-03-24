package com.sangyeop.controller;

import com.sangyeop.domain.User;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("진입");
        if (currentUser==null) {
            currentUser =  userRepository.findById(payload.get("id").toString());
        }
        return "/todo/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        System.out.println(currentUser.getId());
        model.addAttribute("todoList", toDoRepository.findAllByOrderByIdx());
        return "/todo/list";
    }

}
