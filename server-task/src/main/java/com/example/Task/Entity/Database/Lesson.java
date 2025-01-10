package com.example.Task.Entity.Database;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String language;
    private String init_code;
    private String mission;
    @Lob
    private String guild;
    private Integer order_level;
    private Integer id_course;
    private Integer id_game_lesson;
}
