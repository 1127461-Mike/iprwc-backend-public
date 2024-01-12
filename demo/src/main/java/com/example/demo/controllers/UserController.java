package com.example.demo.controllers;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping()
    public List<User> getAllUsers(){
        return userService.findAll();
    }
    @PostMapping("/registrate")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User newUser = userService.registerUser(registrationDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping("/registrate/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRegistrationDto registrationDto) {
        User newUser = userService.registerAdmin(registrationDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/getrole")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RoleDto> getRole(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);

        if (user != null) {
            RoleDto roleDto = new RoleDto(user.getRole());
            return ResponseEntity.ok(roleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
