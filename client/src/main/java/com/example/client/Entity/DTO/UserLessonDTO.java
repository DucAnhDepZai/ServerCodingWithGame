package com.example.client.Entity.DTO;


import com.example.client.Entity.Model.Lesson;
import lombok.Data;

@Data
public class UserLessonDTO {
    private Integer id;
    private Lesson lesson;
    private boolean isComplete;
}
