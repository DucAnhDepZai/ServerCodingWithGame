package com.example.client.Entity.Model;

import lombok.Data;


@Data

public class Course {
    private int id;
    private String name;
    private String description;
    private String image_url;
    private String full_description;
    private int original_price;
    private double rating;
    private boolean is_popular;
}

