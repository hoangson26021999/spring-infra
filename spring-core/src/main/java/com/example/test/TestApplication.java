package com.example.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
