package com.blogapi.blogapi.controller;

import com.blogapi.blogapi.model.Post;
import com.blogapi.blogapi.model.User;
import com.blogapi.blogapi.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final UserRepository userRepository;

    public BlogController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // API für den Abruf der aktuell vorhandenen Blogs/Forumeinträge aus der Datenbank
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        // Implementierung zum Abrufen von Posts aus der Datenbank
        return ResponseEntity.ok().build();
    }

    // API für die Registrierung eines neuen Users
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Benutzername bereits vorhanden
        }
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // API für die Anmeldung eines Users
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(user.getPassword())) {
            // Benutzer authentifiziert, generiere und sende Authentifizierungs-Token
            String token = generateToken();
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Ungültige Anmeldeinformationen
        }
    }

    // API für die Aufnahme eines neuen Forum-Eintrags in die Datenbank
    @PostMapping("/forum")
    public ResponseEntity<Void> addForumEntry(@RequestBody Post post, @RequestHeader("Authorization") String token) {
        // Überprüfung des Authentifizierungstokens und Hinzufügen des Posts zur Datenbank
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Hilfsmethode zum Generieren eines Authentifizierungstokens (vereinfacht)
    private String generateToken() {
        return "randomToken";
    }
}