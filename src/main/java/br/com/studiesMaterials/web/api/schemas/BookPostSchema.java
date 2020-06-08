package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookPostSchema {
    public String subject;
    public String title;
    public String author;
    public String link;
}
