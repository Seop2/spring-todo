package com.sangyeop.service;

import com.sangyeop.domain.User;
import com.sangyeop.domain.UserRequestDto;
import com.sangyeop.domain.UserRole;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author hagome
 * @since 2019-03-30
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /* 유효성이 인증된 User 생성 */
    public User save(UserRequestDto userRequestDto) {
        String id = userRequestDto.getId();
        String pw = userRequestDto.getPw();
        String email = userRequestDto.getEmail();
        UserRole role = new UserRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        role.setRoleName("BASIC");
        User user = User.builder()
                .id(id)
                .passsword(passwordEncoder.encode(pw))
                .email(email)
                .roles(Arrays.asList(role))
                .build();
        return userRepository.save(user);
    }
}
