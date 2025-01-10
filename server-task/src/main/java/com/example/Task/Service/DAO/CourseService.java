package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CourseService extends BaseDAOService {

    public List<Course> getAllCourses() {
        return entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    public Course getCourseById(Integer id) {
        return entityManager.find(Course.class, id);
    }

    @Transactional
    public Course createCourse(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Transactional
    public Course updateCourse(Integer id, Course updatedCourse) {
        updatedCourse.setId(id);
        return entityManager.merge(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Integer id) {
        Course course = getCourseById(id);
        if (course != null) {
            entityManager.remove(course);
        }
    }

    public List<Course> searchByName(String name) {
        String query = "SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(:name)";
        return entityManager.createQuery(query, Course.class)
                .setParameter("name", "%" + name + "%")  // Adding "%" for partial match
                .getResultList();
    }
}
