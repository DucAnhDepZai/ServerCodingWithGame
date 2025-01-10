package com.example.Task.Controller;

import com.example.Task.Entity.Database.Course;
import com.example.Task.Service.DAO.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course updatedCourse) {
        return courseService.updateCourse(id, updatedCourse);
    }
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/search")
    public List<Course> searchByName(@RequestParam String name){
        return courseService.searchByName(name);
    }
}
