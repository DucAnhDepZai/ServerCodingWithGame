package com.example.client.Entity.Model;

import lombok.Data;


@Data

public class Submission {

    private Integer id;
    private String language;
    private String source_code;
    private String result;
    private boolean success;
    private Integer id_userLesson;
}
