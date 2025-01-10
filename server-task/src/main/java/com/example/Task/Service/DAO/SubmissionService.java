package com.example.Task.Service.DAO;

import com.example.Task.Entity.Database.Submission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SubmissionService extends BaseDAOService {

    public List<Submission> getAllSubmissions() {
        return entityManager.createQuery("SELECT sb FROM Submission sb", Submission.class).getResultList();
    }

    public Submission getSubmissionById(Integer id) {
        return entityManager.find(Submission.class, id);
    }

    @Transactional
    public Submission createSubmission(Submission submission) {
        entityManager.persist(submission);
        return submission;
    }

    @Transactional
    public Submission updateSubmission(Integer id, Submission updatedSubmission) {
        updatedSubmission.setId(id);
        return entityManager.merge(updatedSubmission);
    }

    @Transactional
    public Submission updateSubmissionResult(Integer id, String result) {
        Submission updatedSubmission = getSubmissionById(id);
        updatedSubmission.setResult(result);
        return entityManager.merge(updatedSubmission);
    }

}
