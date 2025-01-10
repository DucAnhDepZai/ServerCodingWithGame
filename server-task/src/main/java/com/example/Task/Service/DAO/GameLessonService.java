package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.GameLesson;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GameLessonService extends BaseDAOService {

    public List<GameLesson> getAllGameLessons() {
        return entityManager.createQuery("SELECT l FROM GameLesson l", GameLesson.class).getResultList();
    }

    public GameLesson getGameLessonById(Integer id) {
        return entityManager.find(GameLesson.class, id);
    }

    @Transactional
    public GameLesson createGameLesson(GameLesson gameLesson) {
        entityManager.persist(gameLesson);
        return gameLesson;
    }

    @Transactional
    public GameLesson updateGameLesson(Integer id, GameLesson updatedGameLesson) {
        updatedGameLesson.setId(id);
        return entityManager.merge(updatedGameLesson);
    }

    @Transactional
    public void deleteGameLesson(Integer id) {
        GameLesson gameLesson = getGameLessonById(id);
        if (gameLesson != null) {
            entityManager.remove(gameLesson);
        }
    }
}
