package com.sangyeop.controller;

import com.sangyeop.domain.User;
import com.sangyeop.domain.UserRole;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author hagome
 * @since  2019-03-29
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/sign_in")
    public String getSignIn() {
        return "/login/sign_in";
    }

    @GetMapping("/sign_up")
    public String getSignUp() {
        return "/login/sign_up";
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> postSingUp(@RequestBody Map<String ,String> payload) {
        /* 회원가입 예외처리 */
        String  id = payload.get("id");
        String  pw = payload.get("pw");
        String  email = payload.get("email");

        if (id == null || id.length()==0) {
            return new ResponseEntity<>("아이디를 확인해 주세요", HttpStatus.FORBIDDEN);
        }
        if (pw == null || pw.length()==0) {
            return new ResponseEntity<>("비밀번호가 너무 짧습니다.", HttpStatus.FORBIDDEN);
        }
        if (email ==null || email.length()==0) {
            return new ResponseEntity<>("이메일을 확인해 주세요.", HttpStatus.FORBIDDEN);
        }
        if (userRepository.findById(id) != null) {
            return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.FORBIDDEN);
        }
        /* 회원가입 예외처리 END */


        /* 회원가입 성공 */
        UserRole role = new UserRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        role.setRoleName("BASIC");
        User user = User.builder()
                .id(id)
                .passsword(passwordEncoder.encode(pw))
                .email(email)
                .roles(Arrays.asList(role))
                .build();
        userRepository.save(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

}
