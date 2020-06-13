package br.com.studiesMaterials.web.api.schemas;

import java.util.UUID;

public class ArticleResponse {
    private UUID id;
    private String subject;
    private String link;

    public ArticleResponse(String id, String subject, String link) {
        this.id = UUID.fromString(id);
        this.subject = subject;
        this.link = link;
    }
}
