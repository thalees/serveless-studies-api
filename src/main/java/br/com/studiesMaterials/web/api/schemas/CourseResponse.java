package br.com.studiesMaterials.web.api.schemas;

public class CourseResponse {
    public String id;
    public String name;
    public String platform;
    public Float price;

    public CourseResponse(String id, String name,String platform, String price){
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.price = Float.parseFloat(price);
    }
}
