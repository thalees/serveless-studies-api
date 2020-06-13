package br.com.studiesMaterials.web.api.schemas;

public class StudentResponse {
    public String id;
    public String username;

    public StudentResponse(String id,String username) {
        this.id = id;
        this.username = username;
    }
}
