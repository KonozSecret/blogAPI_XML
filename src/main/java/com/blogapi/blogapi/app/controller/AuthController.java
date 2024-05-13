package com.blogapi.blogapi.app.controller;

import com.blogapi.blogapi.app.model.LoginForm;
import com.blogapi.blogapi.app.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String authToken = authenticationService.authenticate(loginForm.getUsername(), loginForm.getPassword());
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
