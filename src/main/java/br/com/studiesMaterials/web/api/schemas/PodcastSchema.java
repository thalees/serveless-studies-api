package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PodcastSchema {
    public UUID id;
    public UUID student_id;
    public String subject;
    public int time;
    public String link;
}
