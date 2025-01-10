package com.example.client.Entity.Model;

import lombok.Data;

@Data

public class User {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private String password;
}
