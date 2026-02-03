package com.example.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class TestApplication {

    @Value("${a}")
    private String a;
    @Value("${b}")
    private String b;
    @Value("${d}")
    private String d;
    @Value("${e}")
    private String e;

    @Value("${q.e.r}")
    private String QER;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);

        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());
        System.out.println(Instant.now());

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());
        System.out.println(Instant.now());


    }

    @Bean
    CommandLineRunner printAOnStartup() {
        return args -> System.out.println("a: " + a);
    }

    @Bean
    CommandLineRunner printAOnStartupB() {
        return args -> System.out.println("b: " + b);
    }

    @Bean
    CommandLineRunner printAOnStartupD() {
        return args -> System.out.println("d: " + d);
    }

    @Bean
    CommandLineRunner printAOnStartupE() {
        return args -> System.out.println("e: " + e);
    }

    @Bean
    CommandLineRunner printAOnStartupQER() {
        return args -> System.out.println("QER: " + QER);
    }

}
