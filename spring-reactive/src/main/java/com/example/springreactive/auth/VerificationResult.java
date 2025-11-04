package com.example.springreactive.auth;

import lombok.Data;

import java.util.List;

@Data
public class VerificationResult {
    private boolean validated;
    private String jti;
    private String sub;
    private String name;
    private List<String> roles;
}
