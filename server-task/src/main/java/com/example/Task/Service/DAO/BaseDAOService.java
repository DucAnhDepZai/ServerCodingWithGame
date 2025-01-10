package com.example.Task.Service.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDAOService {
    @PersistenceContext
    public EntityManager entityManager;
}
