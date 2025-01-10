package com.example.Task.Entity.DTO;

import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Entity.Database.LessonData;
import com.example.Task.Service.DAO.LessonService;
import lombok.Data;

@Data
public class LessonDataDTO {
    private Integer id;
    private String language;
    private String top_source;
    private String bot_source;
    private String input;
    private Lesson lesson;
    public LessonDataDTO(LessonData lessonData, Lesson lesson) {
        this.id = lessonData.getId();
        this.top_source = lessonData.getTop_source();
        this.bot_source = lessonData.getBot_source();
        this.input = lessonData.getInput();
        this.lesson = lesson;
    }
}
