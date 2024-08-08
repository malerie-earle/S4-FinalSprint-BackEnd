package com.keyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication(scanBasePackages = {"com.keyin", "com.keyin.Config"})
public class RESTServiceApplication {
    public static void main (String [] args){
        SpringApplication.run(RESTServiceApplication.class, args);
    }

}