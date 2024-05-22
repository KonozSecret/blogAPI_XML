package com.blogapi.blogapi.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


// Entity-Klasse für die Benutzer
@Entity
@Table(name ="users")
@XmlRootElement
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @XmlElement
    public String getPassword() {
        return this.password;
    }
    @XmlElement
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
