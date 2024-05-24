package com.blogapi.blogapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "posts")
@JacksonXmlRootElement(localName = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "title")
    @Column(nullable = false)
    private String title;

    @JacksonXmlProperty(localName = "content")
    @Column(nullable = false)
    private String content;

    @JacksonXmlProperty(localName = "createdAt")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JacksonXmlProperty(localName = "author")
    @Column(nullable = false)
    private String author;

    // Standardkonstruktor
    public Post() {
        this.createdAt = LocalDateTime.now(); // Setze das Erstellungsdatum automatisch
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
