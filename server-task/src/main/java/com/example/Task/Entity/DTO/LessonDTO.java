package com.example.Task.Entity.DTO;

import com.example.Task.Entity.Database.Course;
import com.example.Task.Entity.Database.Lesson;
import lombok.Data;

@Data
public class LessonDTO {
    private Integer id;
    private String name;
    private String content;
    private Course course;

}
