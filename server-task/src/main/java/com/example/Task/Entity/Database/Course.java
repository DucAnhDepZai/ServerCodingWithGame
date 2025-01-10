package com.example.Task.Entity.Database;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String image_url;
    @Lob
    private String full_description;
    private int original_price;
    private double rating;
    private boolean is_popular;
}
