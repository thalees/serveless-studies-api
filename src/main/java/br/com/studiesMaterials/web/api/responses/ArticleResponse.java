package br.com.studiesMaterials.web.api.responses;

import java.util.UUID;

public class ArticleResponse {
    UUID id;
    String subject;
    String link;

    public ArticleResponse(String id, String subject, String link) {
        this.id = UUID.fromString(id);
        this.subject = subject;
        this.link = link;
    }
}
