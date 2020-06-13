package br.com.studiesMaterials.web.api.responses;

import java.util.UUID;

public class CourseResponse {
    UUID id;
    String name;
    String platform;
    Float price;

    public CourseResponse(String id, String name, String platform, Float price){
        this.id = UUID.fromString(id);
        this.name = name;
        this.platform = platform;
        this.price = price;
    }
}
