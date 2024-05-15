package com.blogapi.blogapi.service;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public boolean addPost(Post post) {
        postRepository.save(post);
        return true;
    }


}
