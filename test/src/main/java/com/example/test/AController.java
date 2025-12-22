package com.example.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

    @Value("${a}")
    private String a;
    @Value("${b}")
    private String b;
    @Value("${d}")
    private String d;

    @GetMapping("/a")
    public String getA() {
        return a;
    }

    @GetMapping("/b")
    public String getB() {
        return b;
    }

    @GetMapping("/d")
    public String getD() {
        return d;
    }
}
