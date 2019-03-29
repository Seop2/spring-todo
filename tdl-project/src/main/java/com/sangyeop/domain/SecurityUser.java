package com.sangyeop.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hagome
 * @since  2019-03-29
 */
public class SecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    public SecurityUser(com.sangyeop.domain.User user) {
        super(user.getId(), user.getPasssword(), makeGrantedAuthority(user.getRoles()));
    }

    /* User Role을  GrantedAuthority로 변경 */
    private static List<GrantedAuthority> makeGrantedAuthority(List<UserRole> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }
}
