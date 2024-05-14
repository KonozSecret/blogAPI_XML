package com.blogapi.blogapi.app.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.blogapi.blogapi.app.model.User;
import com.blogapi.blogapi.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String authToken = authenticationService.authenticate(user.getUsername(), user.getPassword());
        if (authToken != null) {
            return ResponseEntity.ok(authToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/isUserLoggedIn")
    public ResponseEntity<String> isUserLoggedIn(@RequestHeader("Authorization") String authToken) {
        if (authToken != null && authenticationService.isValidToken(authToken)) {
            return ResponseEntity.ok("User is logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
    }
}
