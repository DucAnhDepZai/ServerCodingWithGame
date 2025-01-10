package com.example.client.Entity.Model;


import lombok.Data;


@Data
public class UserCourse {
    private int id;
    private int user_id;
    private int course_id;
    private String status;
}
