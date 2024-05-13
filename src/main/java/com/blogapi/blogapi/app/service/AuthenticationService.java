package com.blogapi.blogapi.app.service;

import com.blogapi.blogapi.app.model.User;
import com.blogapi.blogapi.app.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Generiere und gib das Authentifizierungstoken zurück
            String authToken = generateAuthToken();
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
        try {
            // Dekodiere den Base64-Token zurück zu Byte-Array
            byte[] decodedBytes = Base64.getDecoder().decode(authToken);

            // Überprüfe, ob die Länge des Byte-Arrays 64 ist
            if(decodedBytes.length != 64)
                return false;

            // Der Token wird als gültig betrachtet, wenn keine Ausnahme auftritt
            return true;
        } catch (IllegalArgumentException e) {
            // Wenn beim Dekodieren eine IllegalArgumentException auftritt, ist der Token ungültig
            return false;
        }
    }
}
