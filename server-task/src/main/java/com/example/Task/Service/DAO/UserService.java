package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    // Lấy thông tin người dùng theo ID
    public User getUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    // Lấy thông tin người dùng theo tên đăng nhập
    public User getUserByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    // Tạo người dùng mới
    @Transactional
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    // Cập nhật thông tin người dùng
    @Transactional
    public User updateUser(Integer id, User updatedUser) {
        updatedUser.setId(id);
        return entityManager.merge(updatedUser);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public User authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        return user;
    }
}
