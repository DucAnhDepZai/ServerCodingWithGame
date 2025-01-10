package com.example.client.Entity.DTO;


import com.example.client.Entity.Model.GameLesson;
import lombok.Data;

@Data
public class LessonDTO {
    private Integer id;
    private String name;
    private String language;
    private String init_code;
    private String guild;
    private String mission;
    private int order;
    private Integer id_course;
    private GameLesson gameLesson;
}
