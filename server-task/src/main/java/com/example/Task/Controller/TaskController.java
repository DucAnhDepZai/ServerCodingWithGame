package com.example.Task.Controller;

import com.example.Task.Entity.DTO.SubmissionDTO;
import com.example.Task.Entity.Database.Submission;
import com.example.Task.Service.Task.RunCodeService;
import com.example.Task.Service.Task.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/process")
public class TaskController {

    @Autowired
    private VerifyService verifyService;
    @PostMapping("/run_code")
    public Submission startVerify(@RequestBody Submission submission) throws Exception {
        return verifyService.startVerify(submission);
    }

}
