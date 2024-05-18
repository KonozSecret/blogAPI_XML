package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        String authToken = authenticationService.authenticate(user.getUsername(), user.getPassword());
        Map<String, String> response = new HashMap<>();
        if (authToken != null) {
            response.put("token", authToken);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/isUserLoggedIn")
    public ResponseEntity<Map<String, String>> isUserLoggedIn(@RequestHeader("Authorization") String authToken) {
        Map<String, String> response = new HashMap<>();
        if (authToken != null) {
            if (authenticationService.isValidToken(authToken)) {
                response.put("message", "User is logged in");
                return ResponseEntity.ok(response);
            }
        }
        response.put("message", "User is not logged in");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authToken) {
        if (authToken != null && authenticationService.invalidateToken(authToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


}
