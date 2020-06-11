package br.com.studiesMaterials.web.api.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponse {
    public String id;
    public String username;

    public StudentResponse(String id,String username) {
        this.id = id;
        this.username = username;
    }
}
