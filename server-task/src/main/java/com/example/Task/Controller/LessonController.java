package com.example.Task.Controller;

import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Service.DAO.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    // Lấy tất cả bài học
    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }
    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable Integer id) {
        return lessonService.getLessonById(id);
    }
    @GetMapping("next/{id}")
    public Lesson getNextLessonById(@PathVariable Integer id) {
        return lessonService.getNextLessonById(id);
    }
    @GetMapping("/course/{courseId}")
    public List<Lesson> getAllLessonsByCourseId(@PathVariable Integer courseId) {
        return lessonService.getAllLessonsByCourseId(courseId);
    }
    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonService.createLesson(lesson);
    }

    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable Integer id, @RequestBody Lesson updatedLesson) {
        return lessonService.updateLesson(id, updatedLesson);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
    }
}
