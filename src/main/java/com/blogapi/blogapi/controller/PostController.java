package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.service.AuthenticationService;
import com.blogapi.blogapi.service.PostService;
import jakarta.xml.bind.annotation.XmlRootElement;
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
    private PostService postService;
    @Autowired
    private AuthenticationService authenticationService;

    @XmlRootElement
    public static class Response {
        public String message;
        public String token;

        public Response() {}

        public Response(String message, String token) {
            this.message = message;
            this.token = token;
        }
    }

    @PostMapping(value = "/addPost", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Response> addPost(@RequestHeader("Authorization") String authToken, @RequestBody Post post) {
        try {
            if (authToken == null || !authenticationService.isValidToken(authToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response("Unauthorized access", null));
            }

            String username = authenticationService.extractUsernameFromToken(authToken);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response("Invalid token", null));
            }

            post.setAuthor(username);
            post.setCreatedAt(LocalDateTime.now());

            boolean added = postService.addPost(post);
            if (added) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new Response("Blog-Eintrag erfolgreich hinzugefügt", authToken));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response("Fehler beim Hinzufügen des Blog-Eintrags", authToken));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Internal Server Error", null));
        }
    }
}
