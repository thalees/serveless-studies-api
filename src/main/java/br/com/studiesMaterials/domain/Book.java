package br.com.studiesMaterials.domain;

import java.util.UUID;

public class Book {
    private UUID id;
    private UUID userId;
    private String subject;
    private String title;
    private String author;
    private String link;

    public Book(String id, String userId, String subject, String title, String author, String link) {
        this.id = UUID.fromString(id);
        this.userId = UUID.fromString(userId);
        this.subject = subject;
        this.title = title;
        this.author = author;
        this.link = link;
    }
}
