package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.service.AuthenticationService;
import com.blogapi.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")public class PostGetController {
    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/getBlog", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Post>> getAllPosts(@RequestHeader("Authorization") String authToken) {
        if (!authenticationService.isValidToken(authToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Post> getPostById(@RequestHeader("Authorization") String authToken, @PathVariable("id") Long id) {
        if (!authenticationService.isValidToken(authToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Post> postOptional = postService.getPostById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }
}
