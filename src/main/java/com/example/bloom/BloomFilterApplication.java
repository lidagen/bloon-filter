package com.example.bloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pri.tool","com.example.bloom"})
public class BloomFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomFilterApplication.class, args);
    }

}
