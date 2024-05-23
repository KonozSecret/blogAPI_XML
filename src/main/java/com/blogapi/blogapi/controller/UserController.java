package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        boolean registered = userService.registerUser(user);
        Map<String, String> response = new HashMap<>();
        if (registered) {
            response.put("message", "erfolgreich");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Benutzername bereits vergeben");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
