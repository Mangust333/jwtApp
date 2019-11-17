package com.example.jwtapp.rest;

import com.example.jwtapp.dto.AdminDto;
import com.example.jwtapp.dto.UserDto;
import com.example.jwtapp.model.User;
import com.example.jwtapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminRestControllerV1 {

    private final UserService userService;

    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminDto result = AdminDto.fromAdmin(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
