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
        if (authToken != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", authToken);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
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
    //test asdas
}
