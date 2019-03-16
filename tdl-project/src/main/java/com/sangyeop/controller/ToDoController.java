package com.sangyeop.controller;

import com.sangyeop.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoRepository toDoRepository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("todoList", toDoRepository.findAll());
        return "/todo/list";
    }

}
