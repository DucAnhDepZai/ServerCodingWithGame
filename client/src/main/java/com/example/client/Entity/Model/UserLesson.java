package com.example.client.Entity.Model;

import lombok.Data;


@Data

public class UserLesson {
    private Integer id;
    private Integer id_lesson;
    private Integer id_user_course;
    private boolean isComplete;
}
