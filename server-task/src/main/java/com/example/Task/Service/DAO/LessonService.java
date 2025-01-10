package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Entity.Database.UserCourse;
import com.example.Task.Entity.Database.UserLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LessonService extends BaseDAOService {

    public List<Lesson> getAllLessons() {
        return entityManager.createQuery("SELECT l FROM Lesson l", Lesson.class).getResultList();
    }

    public Lesson getLessonById(Integer id) {
        return entityManager.find(Lesson.class, id);
    }

    public Lesson getNextLessonById(Integer id) {
        Lesson currentLesson = entityManager.find(Lesson.class, id);

        if (currentLesson == null) {
            return null;
        }

        return entityManager.createQuery(
                        "SELECT l FROM Lesson l WHERE l.id_course = :courseId AND l.order_level > :currentOrderLevel ORDER BY l.order_level ASC", Lesson.class)
                .setParameter("courseId", currentLesson.getId_course())
                .setParameter("currentOrderLevel", currentLesson.getOrder_level())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Lesson> getAllLessonsByCourseId(Integer courseId) {
        return entityManager.createQuery(
                        "SELECT l FROM Lesson l WHERE l.id_course = :courseId ORDER BY l.order_level", Lesson.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    @Transactional
    public Lesson createLesson(Lesson lesson) {
        entityManager.persist(lesson);
        return lesson;
    }

    @Transactional
    public Lesson updateLesson(Integer id, Lesson updatedLesson) {
        updatedLesson.setId(id);
        return entityManager.merge(updatedLesson);
    }

    @Transactional
    public void deleteLesson(Integer id) {
        Lesson lesson = getLessonById(id);
        if (lesson != null) {
            entityManager.remove(lesson);
        }
    }
}
