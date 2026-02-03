package com.example.springreactiveclean.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {

    @GetMapping(value = "/hello")
    public Flux<String> hello() {
        String [] a = {"hello" , "world" , "reactive" , "spring" , "boot"};
        return Flux.fromArray(a).map(e -> {
            e.toLowerCase();
            System.out.println(e);
            e.toUpperCase();
            System.out.println(e);
            return e.toUpperCase();
        });
    }
}
