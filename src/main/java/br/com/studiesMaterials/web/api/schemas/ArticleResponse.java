package br.com.studiesMaterials.web.api.schemas;

import java.util.UUID;

public class ArticleResponse {
    private UUID id;
    private UUID studentId;
    private String subject;
    private String link;

    public ArticleResponse(String id, String studentId, String subject, String link) {
        this.id = UUID.fromString(id);
        this.studentId = UUID.fromString(studentId);
        this.subject = subject;
        this.link = link;
    }
}
