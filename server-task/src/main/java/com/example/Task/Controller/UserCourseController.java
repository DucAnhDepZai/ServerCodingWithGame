package com.example.Task.Controller;

import com.example.Task.Entity.Database.UserCourse;
import com.example.Task.Service.DAO.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-course")
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @GetMapping
    public List<UserCourse> getAllUserCourses() {
        return userCourseService.getAllCourses();
    }

    @GetMapping("/find")
    public UserCourse getUserCourseByUserIdAndCourseId(@RequestParam int userId, @RequestParam int courseId) {
        UserCourse userCourse = userCourseService.getUserCourse(userId, courseId);
        if (userCourse == null) {
            return new UserCourse();
        }
        return userCourse;
    }

    @PostMapping
    public UserCourse createUserCourse(@RequestBody UserCourse userCourse) {
        return userCourseService.createCourse(userCourse);
    }

    @PutMapping("/{id}")
    public UserCourse updateUserCourse(
            @PathVariable Integer id,
            @RequestBody UserCourse updatedUserCourse) {
        return userCourseService.updateCourse(id, updatedUserCourse);
    }

}
