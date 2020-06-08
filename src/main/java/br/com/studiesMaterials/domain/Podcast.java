package br.com.studiesMaterials.domain;

import lombok.AllArgsConstructor;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
public class Podcast {
    private UUID id;
    private UUID userId;
    private String subject;
    private int time;
    private String link;

    public Podcast(String id, String userId, String subject, int time, String link) {
        this.id = UUID.fromString(id);
        this.userId = UUID.fromString(userId);
        this.subject = subject;
        this.time = time;
        this.link = link;
    }
}
