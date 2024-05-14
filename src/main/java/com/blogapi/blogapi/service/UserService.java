package com.blogapi.blogapi.service;

import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Benutzername bereits vergeben
        }

        userRepository.save(user);
        return true; // Benutzer erfolgreich registriert
    }


}