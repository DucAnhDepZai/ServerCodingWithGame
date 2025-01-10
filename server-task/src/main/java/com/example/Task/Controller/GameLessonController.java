package com.example.Task.Controller;

import com.example.Task.Entity.Database.GameLesson;
import com.example.Task.Service.DAO.GameLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamelesson")
@CrossOrigin(origins = "http://localhost:3000")
public class GameLessonController {

    @Autowired
    private GameLessonService gameLessonService;

    @GetMapping
    public List<GameLesson> getAllGameLessons() {
        return gameLessonService.getAllGameLessons();
    }

    @GetMapping("/{id}")
    public GameLesson getGameLessonById(@PathVariable Integer id) {
        return gameLessonService.getGameLessonById(id);
    }
    @PostMapping
    public GameLesson createGameLesson(@RequestBody GameLesson gameLesson) {
        return gameLessonService.createGameLesson(gameLesson);
    }
    @PutMapping("/{id}")
    public GameLesson updateGameLesson(@PathVariable Integer id, @RequestBody GameLesson updatedGameLesson) {
        return gameLessonService.updateGameLesson(id, updatedGameLesson);
    }
    @DeleteMapping("/{id}")
    public void deleteGameLesson(@PathVariable Integer id) {
        gameLessonService.deleteGameLesson(id);
    }
}
