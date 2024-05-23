package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.service.AuthenticationService;
import com.blogapi.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authToken, @RequestBody Post post) {
        if (authToken == null || !authenticationService.isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("<response>Unauthorized access</response>");
        }

        String username = authenticationService.extractUsernameFromToken(authToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("<response>Invalid token</response>");
        }

        post.setAuthor(username);
        post.setCreatedAt(LocalDateTime.now());

        boolean added = postService.addPost(post);
        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).body("<response>Blog-Eintrag erfolgreich hinzugefügt</response>");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("<response>Fehler beim Hinzufügen des Blog-Eintrags</response>");
        }
    }
}
