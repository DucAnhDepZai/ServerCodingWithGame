package com.example.client.Entity.Model;

import lombok.Data;

@Data

public class Lesson {
    private Integer id;
    private String name;
    private String language;
    private String init_code;
    private String guild;
    private String mission;
    private int order;
    private Integer id_course;
    private Integer id_game_lesson;
}
