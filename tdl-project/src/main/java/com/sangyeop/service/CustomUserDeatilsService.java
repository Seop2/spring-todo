package com.sangyeop.service;

import com.sangyeop.domain.SecurityUser;
import com.sangyeop.domain.User;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDeatilsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username);

        /* 존재하지 않는 사용자일 경우 */
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new SecurityUser(user);

    }
}
