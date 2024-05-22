package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @XmlRootElement
    public static class Response {
        public String message;
        public String token;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Response> login(@RequestBody User user) {
        String authToken = authenticationService.authenticate(user.getUsername(), user.getPassword());
        Response response = new Response();
        if (authToken != null) {
            response.token = authToken;
            return ResponseEntity.ok(response);
        } else {
            response.message = "Invalid username or password";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping(value = "/isUserLoggedIn", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Response> isUserLoggedIn(@RequestHeader("Authorization") String authToken) {
        Response response = new Response();
        if (authToken != null) {
            if (authenticationService.isValidToken(authToken)) {
                response.message = "User is logged in";
                return ResponseEntity.ok(response);
            }
        }
        response.message = "User is not logged in";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Response> logout(@RequestHeader("Authorization") String authToken) {
        if (authToken != null && authenticationService.invalidateToken(authToken)) {
            Response response = new Response();
            response.message = "Logged out successfully";
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response();
            response.message = "Invalid token";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
