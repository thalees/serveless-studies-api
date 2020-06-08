package br.com.studiesMaterials.domain;

import java.util.UUID;

public class Student {
    private UUID id;
    private String username;

    public Student(String id, String username){
        this.id = UUID.fromString(id);
        this.username = username;
    }
}
