package com.blogapi.blogapi.app.controller;

import com.blogapi.blogapi.app.model.Post;
import com.blogapi.blogapi.app.service.AuthenticationService;
import com.blogapi.blogapi.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // Hier kannst du sicherstellen, dass der Benutzer angemeldet ist und den Post hinzufügen

        boolean added = postService.addPost(post);
        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Blog-Eintrag erfolgreich hinzugefügt");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Hinzufügen des Blog-Eintrags");
        }
    }


}