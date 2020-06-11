package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    public String id;
    public String subject;
    public String title;
    public String author;
    public String link;

    public BookResponse(String id, String subject, String title, String author, String link) {
        this.id = id;
        this.subject = subject;
        this.title = title;
        this.author = author;
        this.link = link;
    }
}
