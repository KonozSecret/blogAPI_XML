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

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        String authToken = authenticationService.authenticate(user.getUsername(), user.getPassword());
        Map<String, Object> response = new HashMap<>();
        if (authToken != null) {
            response.put("token", authToken);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping(value = "/isUserLoggedIn", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, Object>> isUserLoggedIn(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        if (authToken != null) {
            if (authenticationService.isValidToken(authToken)) {
                response.put("message", "User is logged in");
                return ResponseEntity.ok(response);
            }
        }
        response.put("message", "User is not logged in");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        if (authToken != null && authenticationService.invalidateToken(authToken)) {
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
