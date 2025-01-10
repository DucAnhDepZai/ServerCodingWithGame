package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.Course;
import com.example.Task.Entity.Database.UserCourse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserCourseService extends BaseDAOService {

    public List<UserCourse> getAllCourses() {
        return entityManager.createQuery("SELECT c FROM UserCourse c", UserCourse.class).getResultList();
    }

    public UserCourse getCourseById(Integer id) {
        return entityManager.find(UserCourse.class, id);
    }

    public UserCourse getUserCourse(int userId, int courseId) {
        String query = "SELECT uc FROM UserCourse uc WHERE uc.user_id = :userId AND uc.course_id = :courseId";
        List<UserCourse> results = entityManager.createQuery(query, UserCourse.class)
                .setParameter("userId", userId)
                .setParameter("courseId", courseId)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Transactional
    public UserCourse createCourse(UserCourse course) {
        entityManager.persist(course);
        return course;
    }

    @Transactional
    public UserCourse updateCourse(Integer id, UserCourse updatedCourse) {
        updatedCourse.setId(id);
        return entityManager.merge(updatedCourse);
    }

}
