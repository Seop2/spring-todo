package com.sangyeop.controller;

import com.sangyeop.domain.User;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/form")
    public String form() {
        return "/login/form";
    }

    @GetMapping("/register")
    public String register() {
        return "/login/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam HashMap formData) {
        User user = User.builder().id(formData.get("id").toString())
                .passsword(formData.get("pw").toString()).email(formData.get("email").toString()).build();
        userRepository.save(user);

        return "redirect:/login/form";
    }

    @PostMapping("/form")
    public ResponseEntity<?> login(@RequestBody HashMap formData) {
        String id = formData.get("id").toString();
        String pw = formData.get("pw").toString();

        if (id.equals("")) return  new ResponseEntity<>("아이디를 입력해주세요.", HttpStatus.FORBIDDEN);
        if (pw.equals("")) return  new ResponseEntity<>("비밀번호를 입력해주세요.", HttpStatus.FORBIDDEN);

        User user;
        if (userRepository.findById(id) != null) {

            //아이디 존재
            user = userRepository.findById(id);

            if (pw.equals(user.getPasssword())) {
                //로그인 성공
                return new ResponseEntity<>("{}", HttpStatus.OK);
            } else  {

                //비밀번호 틀림
                return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.FORBIDDEN);
            }
        } else {

            //존재 하지 않는 아이디

            return new ResponseEntity<>("아이디를 확인해 주세요.", HttpStatus.FORBIDDEN);
        }

    }

}
