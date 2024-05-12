package com.blogapi.blogapi.app.controller;

import com.blogapi.blogapi.app.model.User;
import com.blogapi.blogapi.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Benutzername bereits vergeben", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        return new ResponseEntity<>("Benutzer erfolgreich registriert", HttpStatus.CREATED);
    }
}