package com.example.Task.Entity.Database;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "lessondata")
public class LessonData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String top_source;
    private String bot_source;
    private String input;
    private Integer lesson_id;
}
