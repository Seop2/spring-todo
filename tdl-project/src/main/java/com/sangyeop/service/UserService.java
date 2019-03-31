package com.sangyeop.service;

import com.sangyeop.domain.User;
import com.sangyeop.domain.UserRole;
import com.sangyeop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username);
        /* 존재하지 않는 사용자일 경우 */
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<UserRole> roles = user.getRoles();
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach( role -> list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPw(), list);
    }

    public User save(User user) {
        UserRole role = new UserRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        role.setRoleName("BASIC");
        user.setPw(passwordEncoder.encode(user.getPw()));
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }
}
