package com.example.Task.Controller;

import com.example.Task.Entity.Database.UserLesson;
import com.example.Task.Service.DAO.UserLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userlesson")
public class UserLessonController {

    @Autowired
    private UserLessonService userLessonService;

    // Lấy danh sách tất cả UserLesson
    @GetMapping
    public List<UserLesson> getAllUserLessons() {
        return userLessonService.getAllUserLessons();
    }

    // Lấy UserLesson theo ID
    @GetMapping("/{id}")
    public UserLesson getUserLessonById(@PathVariable Integer id) {
        return userLessonService.getUserLessonById(id);
    }


    @GetMapping("/user-course/{userCourseId}")
    public List<UserLesson> getListUserLesson(@PathVariable Integer userCourseId){
        return userLessonService.getListUserLessonByUserId(userCourseId);
    }

    @GetMapping("/user-course/{userCourseId}/lesson/{lessonId}")
    public UserLesson getUserLessonByUserIdAndLessonId(@PathVariable Integer userCourseId, @PathVariable Integer lessonId) {
        return userLessonService.getUserLessonByUserIdAndLessonId(userCourseId, lessonId);
    }

    @PostMapping
    public UserLesson createUserLesson(@RequestBody UserLesson userLesson) {
        return userLessonService.createUserLesson(userLesson);
    }

    @GetMapping("register/{id}")
    public List<UserLesson> createListUserLessonByIdUserCourse(@PathVariable Integer id) {
        return userLessonService.createListUserLessonByIdUserCourse(id);
    }

    @PutMapping("/{id}")
    public UserLesson updateUserLesson(@PathVariable Integer id, @RequestBody UserLesson updatedUserLesson) {
        return userLessonService.updateUserLesson(id, updatedUserLesson);
    }

    @DeleteMapping("/{id}")
    public void deleteUserLesson(@PathVariable Integer id) {
        userLessonService.deleteUserLesson(id);
    }

    @PutMapping("/{id}/complete")
    public UserLesson markLessonComplete(@PathVariable Integer id) {
        System.out.println(id);
        return userLessonService.markLessonComplete(id);
    }
}
