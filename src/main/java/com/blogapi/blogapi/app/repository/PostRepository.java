package com.blogapi.blogapi.app.repository;

import com.blogapi.blogapi.app.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Hier können benutzerdefinierte Abfragen definiert werden, falls erforderlich
}