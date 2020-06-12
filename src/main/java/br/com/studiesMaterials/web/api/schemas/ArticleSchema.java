package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleSchema {
    public String subject;
    public String link;
}
