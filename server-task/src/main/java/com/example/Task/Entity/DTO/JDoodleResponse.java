package com.example.Task.Entity.DTO;
import lombok.Data;

@Data
public class JDoodleResponse {
    private String output;
    private int statusCode;
    private String memory;
    private String cpuTime;
}
