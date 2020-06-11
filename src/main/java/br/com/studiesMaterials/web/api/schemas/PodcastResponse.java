package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PodcastResponse {
    public String id;
    public String subject;
    public String time;
    public String link;

    public PodcastResponse(String id, String subject, String title, String time, String link) {
        this.id = id;
        this.subject = subject;
        this.time = title;
        this.link = link;
    }
}
