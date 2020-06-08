package br.com.studiesMaterials.domain;

import java.util.UUID;

public class Article {
    private UUID id;
    private UUID studentId;
    private String subject;
    private String link;

    public Article(String id, String studentId, String subject, String link) {
        this.id = UUID.fromString(id);
        this.studentId = UUID.fromString(studentId);
        this.subject = subject;
        this.link = link;
    }
}
