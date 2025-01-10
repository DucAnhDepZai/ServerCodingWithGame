package com.example.client.Entity.DTO;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Integer id;
    private String language;
    private String source_code;
    private String result;
    private boolean success;
    private Integer id_lesson;
    private Integer id_user;

}
