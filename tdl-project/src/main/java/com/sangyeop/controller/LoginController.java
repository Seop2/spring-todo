package com.sangyeop.controller;

import com.sangyeop.domain.User;
import com.sangyeop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hagome
 * @since 2019-03-29
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;

    /* 로그인 페이지 */
    @GetMapping("/sign_in")
    public String getSignIn() {
        return "/login/sign_in";
    }

    /* 회원가입 페이지 */
    @GetMapping("/sign_up")
    public String getSignUp() {
        return "/login/sign_up";
    }

    // TODO: 2019-04-01 회원가입 유효성 검사 
    @PostMapping("/sign_up")
    public ResponseEntity<?> postSingUp(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>("{}",HttpStatus.CREATED);
    }
}

