package com.example.client.Entity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProcessRequest {
    private String language;
    private List<FileDTO> files;
    private Result result;
}
