package com.blogapi.blogapi.app.controller;

import com.blogapi.blogapi.app.model.Post;
import com.blogapi.blogapi.app.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/getBlog")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/hallo")
    public String hallo(){
        return "hallo";
    }
    // Weitere Endpunkte zum Erstellen, Aktualisieren und Löschen von Einträgen
}