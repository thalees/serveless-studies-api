package br.com.studiesMaterials.web.api.responses;

import java.util.UUID;

public class PodcastResponse {
    UUID id;
    String subject;
    int time;
    String link;

    public PodcastResponse(String id, String subject, int time, String link) {
        this.id = UUID.fromString(id);
        this.subject = subject;
        this.time = time;
        this.link = link;
    }
}
