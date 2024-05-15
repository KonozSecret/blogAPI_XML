package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.service.AuthenticationService;
import com.blogapi.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authToken, @RequestBody Post post) {
        if (authToken == null || !authenticationService.isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        String username = authenticationService.extractUsernameFromToken(authToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        post.setAuthor(username);
        post.setCreatedAt(LocalDateTime.now());

        boolean added = postService.addPost(post);
        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Blog-Eintrag erfolgreich hinzugefügt");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Hinzufügen des Blog-Eintrags");
        }
    }


}