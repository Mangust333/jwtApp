package com.example.jwtapp.rest;

import com.example.jwtapp.dto.AuthenticationRequestDto;
import com.example.jwtapp.dto.AuthenticationResponseDto;
import com.example.jwtapp.model.User;
import com.example.jwtapp.security.jwt.JwtTokenProvider;
import com.example.jwtapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@Slf4j
public class AuthenticationRestControllerV1  {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto request) {
        try {
            String userName = request.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, request.getPassword()));
            User user = userService.findByUsername(userName);
            if (user == null) {
                throw new UsernameNotFoundException("Not found user with username: " + userName);
            }
            String token = jwtTokenProvider.createToken(userName, user.getRoles());
            AuthenticationResponseDto response = new AuthenticationResponseDto(userName, token);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.warn(e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
