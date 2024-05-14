package com.blogapi.blogapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_tokens")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDateTime;

    public AuthToken() {
        // Default constructor required by JPA
    }

    public AuthToken(String token, User user) {
        this.token = token;
        this.user = user;
        // Set the expiry date time to some fixed duration from current time, e.g., 1 day
        this.expiryDateTime = LocalDateTime.now().plusDays(1);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDateTime);
    }
}