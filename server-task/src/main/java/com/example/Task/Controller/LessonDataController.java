package com.example.Task.Controller;

import com.example.Task.Entity.Database.LessonData;
import com.example.Task.Service.DAO.LessonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessondata")
public class LessonDataController {

    @Autowired
    private LessonDataService lessonDataService;

    // Lấy danh sách tất cả LessonData
    @GetMapping
    public List<LessonData> getAllLessonData() {
        return lessonDataService.getAllLessonData();
    }

    // Lấy LessonData theo ID
    @GetMapping("/{id}")
    public LessonData getLessonDataById(@PathVariable Integer id) {
        return lessonDataService.getLessonDataById(id);
    }

    // Tạo LessonData mới
    @PostMapping
    public LessonData createLessonData(@RequestBody LessonData lessonData) {
        return lessonDataService.createLessonData(lessonData);
    }

    // Lấy danh sách LessonData theo LessonId
    @GetMapping("/lesson/{lessonId}")
    public LessonData getLessonDataByLessonId(@PathVariable Integer lessonId) {
        return lessonDataService.getLessonDataByLessonId(lessonId);
    }

    // Lấy LessonData theo LessonId và Language
    @GetMapping("/lesson/{lessonId}/language/{language}")
    public LessonData getLessonDataByLessonIdAndLanguage(@PathVariable Integer lessonId, @PathVariable String language) {
        return lessonDataService.getLessonDataByLessonIdAndLanguage(lessonId, language);
    }

    // Cập nhật LessonData
    @PutMapping("/{id}")
    public LessonData updateLessonData(@PathVariable Integer id, @RequestBody LessonData updatedLessonData) {
        return lessonDataService.updateLessonData(id, updatedLessonData);
    }

    // Xóa LessonData theo ID
    @DeleteMapping("/{id}")
    public void deleteLessonData(@PathVariable Integer id) {
        lessonDataService.deleteLessonData(id);
    }
}
