package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean registered = userService.registerUser(user);
        if (registered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Benutzer erfolgreich registriert");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzername bereits vergeben");
        }
    }

}