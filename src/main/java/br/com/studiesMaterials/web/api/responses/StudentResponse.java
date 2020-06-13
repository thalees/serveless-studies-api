package br.com.studiesMaterials.web.api.responses;

import java.util.UUID;

public class StudentResponse {
    UUID id;
    String username;

    public StudentResponse(String id,String username) {
        this.id = UUID.fromString(id);
        this.username = username;
    }
}
