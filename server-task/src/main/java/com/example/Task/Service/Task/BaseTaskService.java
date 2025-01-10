package com.example.Task.Service.Task;

import com.example.Task.Service.DAO.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTaskService {
    @Autowired
    public CourseService courseService;
    @Autowired
    public LessonService lessonService;
    @Autowired
    public LessonDataService lessonDataService;
    @Autowired
    public SubmissionService submissionService;
    @Autowired
    public UserService userService;
    @Autowired
    public UserLessonService userLessonService;
}
