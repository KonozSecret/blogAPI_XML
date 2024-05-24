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
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/addPost", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authToken, @RequestBody Post post) {
        if (authToken == null || !authenticationService.isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("<message>Unauthorized access</message>");
        }

        String username = authenticationService.extractUsernameFromToken(authToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("<message>Invalid token</message>");
        }

        post.setAuthor(username);
        post.setCreatedAt(LocalDateTime.now());

        boolean added = postService.addPost(post);
        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).body("<message>Blog-Eintrag erfolgreich hinzugefügt</message>");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("<message>Fehler beim Hinzufügen des Blog-Eintrags</message>");
        }
    }

//    @PostMapping("/addPost")
//    public ResponseEntity<String> addPost(@RequestBody Post post, @RequestHeader("Authorization") String authToken) {
//        if (!authenticationService.isValidToken(authToken)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        postService.savePost(post);
//        String response = "<message>Blog-Eintrag erfolgreich hinzugefügt</message>";
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
}
