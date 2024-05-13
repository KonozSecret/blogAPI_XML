package com.blogapi.blogapi.app.service;

import com.blogapi.blogapi.app.model.User;
import com.blogapi.blogapi.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

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