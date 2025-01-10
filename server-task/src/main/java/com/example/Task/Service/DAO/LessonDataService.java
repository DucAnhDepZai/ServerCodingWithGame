package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.LessonData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class LessonDataService extends BaseDAOService {

    public List<LessonData> getAllLessonData() {
        return entityManager.createQuery("SELECT ld FROM LessonData ld", LessonData.class).getResultList();
    }

    public LessonData getLessonDataById(Integer id) {
        return entityManager.find(LessonData.class, id);
    }

    @Transactional
    public LessonData createLessonData(LessonData lessonData) {
        entityManager.persist(lessonData);
        return lessonData;
    }

    public LessonData getLessonDataByLessonId(Integer lessonId) {
        TypedQuery<LessonData> query = entityManager.createQuery(
                "SELECT ld FROM LessonData ld WHERE ld.lesson_id = :lessonId", LessonData.class);
        query.setParameter("lessonId", lessonId);
        return query.getSingleResult();
    }

    public LessonData getLessonDataByLessonIdAndLanguage(Integer lessonId, String language) {
        TypedQuery<LessonData> query = entityManager.createQuery(
                "SELECT ld FROM LessonData ld WHERE ld.lesson_id = :lessonId AND ld.language = :language", LessonData.class);
        query.setParameter("lessonId", lessonId);
        query.setParameter("language", language);
        return query.getSingleResult();
    }

    @Transactional
    public LessonData updateLessonData(Integer id, LessonData updatedLessonData) {
        updatedLessonData.setId(id);
        return entityManager.merge(updatedLessonData);
    }

    @Transactional
    public void deleteLessonData(Integer id) {
        LessonData lessonData = getLessonDataById(id);
        if (lessonData != null) {
            entityManager.remove(lessonData);
        }
    }
}
