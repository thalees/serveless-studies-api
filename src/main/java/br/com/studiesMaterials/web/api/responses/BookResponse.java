package br.com.studiesMaterials.web.api.responses;

import java.util.UUID;

public class BookResponse {
    UUID id;
    String subject;
    String title;
    String author;
    String link;

    public BookResponse(String id, String subject, String title, String author, String link) {
        this.id = UUID.fromString(id);
        this.subject = subject;
        this.title = title;
        this.author = author;
        this.link = link;
    }
}
