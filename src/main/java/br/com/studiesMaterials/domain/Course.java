package br.com.studiesMaterials.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
public class Course {
    private UUID id;
    private UUID studentId;
    private String name;
    private String platform;
    private Float price;

    public Course(String id, String studentId, String name, String platform, String price){
        this.id = UUID.fromString(id);
        this.studentId = UUID.fromString(studentId);
        this.name = name;
        this.platform = platform;
        this.price = Float.parseFloat(price);
    }
}
