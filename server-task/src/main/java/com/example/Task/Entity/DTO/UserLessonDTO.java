package com.example.Task.Entity.DTO;

import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Entity.Database.User;
import com.example.Task.Entity.Database.UserLesson;
import lombok.Data;

@Data
public class UserLessonDTO {
    private Integer id;
    private Lesson lesson;
    private User user;
    private boolean isComplete;
    public UserLessonDTO(UserLesson userLesson, Lesson lesson, User user) {
        this.id = userLesson.getId();
        this.isComplete = userLesson.isComplete();
        this.lesson = lesson;
        this.user = user;
    }

}
