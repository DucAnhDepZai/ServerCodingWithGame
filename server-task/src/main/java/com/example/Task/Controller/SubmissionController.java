package com.example.Task.Controller;

import com.example.Task.Entity.Database.Submission;
import com.example.Task.Service.DAO.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/{id}")
    public Submission getSubmissionById(@PathVariable Integer id) {
        return submissionService.getSubmissionById(id);
    }

    @GetMapping("/{id}/source-code")
    public String getScourceCode(@PathVariable Integer id) {
        return submissionService.getSubmissionById(id).getSource_code();
    }

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionService.createSubmission(submission);
    }

    @PutMapping("/{id}/result")
    public Submission updateSubmissionResult(@PathVariable Integer id, @RequestBody String result) {
        return submissionService.updateSubmissionResult(id, result);
    }
}
