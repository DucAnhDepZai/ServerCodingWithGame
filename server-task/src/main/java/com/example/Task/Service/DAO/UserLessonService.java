package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Entity.Database.UserCourse;
import com.example.Task.Entity.Database.UserLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserLessonService extends BaseDAOService{
    @Autowired
    private LessonService lessonService;

    @Transactional
    public List<UserLesson> createListUserLessonByIdUserCourse(int userCourseId) {

        UserCourse userCourse = entityManager.find(UserCourse.class, userCourseId);
        if (userCourse == null) {
            throw new IllegalArgumentException("UserCourse not found with id: " + userCourseId);
        }
        List<Lesson> lessons = lessonService.getAllLessonsByCourseId(userCourse.getCourse_id());
        List<UserLesson> userLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            UserLesson userLesson = new UserLesson();
            userLesson.setId_user_course(userCourse.getId());
            userLesson.setId_lesson(lesson.getId());
            userLesson.setComplete(false);
            userLessons.add(userLesson);
        }
        for (UserLesson userLesson : userLessons) {
            createUserLesson(userLesson);
        }

        return userLessons;
    }
    public List<UserLesson> getAllUserLessons() {
        return entityManager.createQuery("SELECT ul FROM UserLesson ul", UserLesson.class).getResultList();
    }

    public UserLesson getUserLessonById(Integer id) {
        return entityManager.find(UserLesson.class, id);
    }

    public List<UserLesson> getListUserLessonByUserId(Integer userCourseId) {
        return entityManager.createQuery(
                        "SELECT ul FROM UserLesson ul WHERE ul.id_user_course = :userCourseId", UserLesson.class)
                .setParameter("userCourseId", userCourseId)
                .getResultList();
    }

    public UserLesson getUserLessonByUserIdAndLessonId(Integer userCourseId, Integer lessonId) {
        try {
            return entityManager.createQuery(
                            "SELECT ul FROM UserLesson ul WHERE ul.id_user_course = :userCourseId AND ul.id_lesson = :lessonId", UserLesson.class)
                    .setParameter("userCourseId", userCourseId)
                    .setParameter("lessonId", lessonId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Transactional
    public UserLesson createUserLesson(UserLesson userLesson) {
        entityManager.persist(userLesson);
        return userLesson;
    }

    // Cập nhật UserLesson
    @Transactional
    public UserLesson updateUserLesson(Integer id, UserLesson updatedUserLesson) {
        updatedUserLesson.setId(id);
        return entityManager.merge(updatedUserLesson);
    }

    // Xóa UserLesson
    @Transactional
    public void deleteUserLesson(Integer id) {
        UserLesson userLesson = getUserLessonById(id);
        if (userLesson != null) {
            entityManager.remove(userLesson);
        }
    }

    @Transactional
    public UserLesson markLessonComplete(Integer id) {
        UserLesson userLesson = getUserLessonById(id);
        if (userLesson != null) {
            userLesson.setComplete(true);
            return entityManager.merge(userLesson);
        }
        return null;
    }
}
