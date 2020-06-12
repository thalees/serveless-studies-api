package br.com.studiesMaterials.web.api.schemas;

import java.util.UUID;

public class CourseResponse {
    private UUID id;
    private UUID studentId;
    private String name;
    private String platform;
    private Float price;

    public CourseResponse(String id, String studentId, String name, String platform, String price){
        this.id = UUID.fromString(id);
        this.studentId = UUID.fromString(studentId);
        this.name = name;
        this.platform = platform;
        this.price = Float.parseFloat(price);
    }
}
