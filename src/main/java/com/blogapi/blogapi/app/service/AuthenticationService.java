package com.blogapi.blogapi.app.service;

import com.blogapi.blogapi.app.model.AuthToken;
import com.blogapi.blogapi.app.model.User;
import com.blogapi.blogapi.app.repository.AuthTokenRepository;
import com.blogapi.blogapi.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthTokenRepository authTokenRepository;


    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Generiere und speichere das Authentifizierungstoken
            String authToken = generateAuthToken();
            AuthToken token = new AuthToken(authToken, user);
            authTokenRepository.save(token);
            return authToken;
        } else {
            // Benutzer nicht gefunden oder ungültige Anmeldeinformationen
            return null;
        }
    }

    private String generateAuthToken() {
        // Verwende SecureRandom für kryptographisch sichere Zufallszahlen
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[64];
        secureRandom.nextBytes(tokenBytes);

        // Verwende Base64 zur Codierung des Tokens
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    public boolean isValidToken(String authToken) {
        AuthToken token = authTokenRepository.findByToken(authToken);
        return token != null && !token.isExpired();
    }
}