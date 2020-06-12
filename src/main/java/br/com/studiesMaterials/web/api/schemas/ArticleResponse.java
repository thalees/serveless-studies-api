package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleResponse {
    public String id;
    public String subject;
    public String link;

    public ArticleResponse(String id, String subject, String link){
        this.id = id;
        this.subject = subject;
        this.link = link;
    }
}
