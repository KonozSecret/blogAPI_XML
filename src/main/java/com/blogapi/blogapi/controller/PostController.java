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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/addPost", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, String>> addPost(@RequestHeader("Authorization") String authToken, @RequestBody Post post) {
        Map<String, String> response = new HashMap<>();

        if (authToken == null || !authenticationService.isValidToken(authToken)) {
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String username = authenticationService.extractUsernameFromToken(authToken);
        if (username == null) {
            response.put("message", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        post.setAuthor(username);
        post.setCreatedAt(LocalDateTime.now());

        boolean added = postService.addPost(post);
        if (added) {
            response.put("message", "Blog-Eintrag erfolgreich hinzugefügt");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Fehler beim Hinzufügen des Blog-Eintrags");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
