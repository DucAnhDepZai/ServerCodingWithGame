package com.example.Task.Service.Task;

import com.example.Task.Entity.DTO.JDoodleResponse;
import com.example.Task.Entity.DTO.SubmissionDTO;
import com.example.Task.Entity.Database.Lesson;
import com.example.Task.Entity.Database.LessonData;
import com.example.Task.Entity.Database.Submission;
import com.example.Task.Entity.Database.UserLesson;
import com.example.Task.Websocket.WebSocketController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VerifyService extends BaseTaskService {
    @Autowired
    private WebSocketController websocketController;
    public Submission startVerify(Submission sub) throws Exception {
        UserLesson userLesson = userLessonService.getUserLessonById(sub.getId_userLesson());
        Lesson lesson = lessonService.getLessonById(userLesson.getId_lesson());
        LessonData lessonData = lessonDataService.getLessonDataByLessonId(lesson.getId());
        String top_source = lessonData.getTop_source();
        String bot_source = lessonData.getBot_source();
        if (top_source == null){
            top_source = "";
        }
        if (bot_source == null){
            bot_source = "";
        }
        String source_code = (top_source + "\n" + sub.getSource_code() + "\n" + bot_source);
        System.out.println(source_code);
        String url = "https://api.jdoodle.com/v1/execute";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("clientId", "ea7dd43240a7ec818ea9e5fe84144814");
        requestBody.put("clientSecret", "8f8743fc6ba3422e15a9ba9540f4a15d6baafef5004afb2e0799bd87bacfa784");
        requestBody.put("script", source_code);
        if(lesson.getLanguage().equals("python")){
            requestBody.put("language", "python3");
        }
        else {
            requestBody.put("language",lesson.getLanguage());
        }
        requestBody.put("versionIndex", 5);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<JDoodleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                JDoodleResponse.class
        );

        JDoodleResponse responseAPI = response.getBody();
        sub.setResult(responseAPI.getOutput());
        boolean isSuccess = false;
        if(responseAPI.getStatusCode() == 200) isSuccess = true;
        sub.setSuccess(isSuccess);
        submissionService.createSubmission(sub);
        System.out.println(sub.getResult());
        System.out.println(responseAPI.getStatusCode());
        return sub;
    }

}
