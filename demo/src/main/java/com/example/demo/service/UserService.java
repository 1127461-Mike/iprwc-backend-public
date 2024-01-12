package com.example.demo.service;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setName(registrationDto.getName());
        user.setSurname(registrationDto.getSurname());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAddress(registrationDto.getAddress());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setPostalCode(registrationDto.getPostalCode());

        return user;
    }


    public User registerUser(UserRegistrationDto registrationDto) {
        User user = createUser(registrationDto);
        user.setRole("USER");

        return userRepository.save(user);
    }

    public User registerAdmin(UserRegistrationDto registrationDto) {
        User user = createUser(registrationDto);
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
