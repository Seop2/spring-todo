package com.sangyeop.controller;

import com.sangyeop.domain.User;
import com.sangyeop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/sign_up")
    public ResponseEntity<?> postSingUp(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(sb, HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/sign_up/valid_id")
    public ResponseEntity<?> validID(@RequestBody Map<String, String> payLoad) {
        String id = payLoad.get("id");
        if (userService.isPresentUser(id)) {
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
    }
}

