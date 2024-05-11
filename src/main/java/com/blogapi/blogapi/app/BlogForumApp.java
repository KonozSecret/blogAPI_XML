package com.blogapi.blogapi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.blogapi.blogapi.controller.BlogController; // Import der Controller-Klasse

@SpringBootApplication
public class BlogForumApp {

    public static void main(String[] args) {
        SpringApplication.run(BlogForumApp.class, args);
    }
}
