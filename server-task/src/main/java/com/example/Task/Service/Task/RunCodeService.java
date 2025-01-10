package com.example.Task.Service.Task;

import com.example.Task.Entity.DTO.SubmissionDTO;
import com.example.Task.Entity.Database.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Repository
public class RunCodeService extends BaseTaskService {

    private Submission createSubmission(Submission submission){
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8080/api/submission";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Submission> request = new HttpEntity<>(submission, headers);
        ResponseEntity<Submission> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Submission>() {}
        );
       return responseEntity.getBody();
    }
    private int startVerify(Submission sub){
//         RestTemplate restTemplate = new RestTemplate();
//         String externalServiceUrl = "http://localhost:8084/api/verify";
//         HttpHeaders headers = new HttpHeaders();
//         HttpEntity<Submission> request = new HttpEntity<>(sub, headers);
//         ResponseEntity<Integer> responseEntity = restTemplate.exchange(
//                 externalServiceUrl,
//                 HttpMethod.POST,
//                 request,
//                 new ParameterizedTypeReference<Integer>() {}
//         );
//         return responseEntity.getBody();
        return 0;
    }
    private String getSourceCode(int id){
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8081/api/submission/" + id + "/source-code";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {}
        );
       return responseEntity.getBody();

    }

//    private void setProblemStatus(int id, String s){
//        RestTemplate restTemplate = new RestTemplate();
//        String externalServiceUrl = "http://localhost:8082/api/problem/" + id + "/status";
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<String> request = new HttpEntity<>(s, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                externalServiceUrl,
//                HttpMethod.PUT,
//                request,
//                new ParameterizedTypeReference<String>() {}
//        );
//    }
    private void setSubmissionResult(int id, String s){
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8081/api/submission/" + id +"/result";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(s, headers);
        ResponseEntity<Submission> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<Submission>() {}
        );
    }
//    private void updateUserScore(int id_problem){
//        Integer curScore = getUserScore(1);
//        curScore += getProblemScore(id_problem);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String externalServiceUrl = "http://localhost:8083/api/user/score/1";
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<Integer> request = new HttpEntity<>(curScore, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                externalServiceUrl,
//                HttpMethod.PUT,
//                request,
//                new ParameterizedTypeReference<String>() {}
//        );
//    }
    private int getUserScore(int id){
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8083/api/user/score/" + id;
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {}
        );
        return responseEntity.getBody();
    }
//     private int getProblemScore(int id){
//         RestTemplate restTemplate = new RestTemplate();
//         String externalServiceUrl = "http://localhost:8082/api/problem/" + id + "/score";
//         ResponseEntity<Integer> responseEntity = restTemplate.exchange(
//                 externalServiceUrl,
//                 HttpMethod.GET,
//                 null,
//                 new ParameterizedTypeReference<Integer>() {}
//         );
//         return responseEntity.getBody();
//     }


}
