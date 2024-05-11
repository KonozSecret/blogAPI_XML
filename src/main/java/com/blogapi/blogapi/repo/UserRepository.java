package com.blogapi.blogapi.repo;


import com.blogapi.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository für die Benutzer
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

