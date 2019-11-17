package com.example.jwtapp.security;

import com.example.jwtapp.model.User;
import com.example.jwtapp.security.jwt.JwtUser;
import com.example.jwtapp.security.jwt.JwtUserFactory;
import com.example.jwtapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + userName + "not found");
        }
        JwtUser jwtUser = JwtUserFactory.created(user);
        log.info("loadUsername - user with name {} successfully loaded", userName);
        return jwtUser;
    }
}
