package com.example.spring_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome(){
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                " <head>\n" +
                " <title>Welcome</title>\n" +
                " </head>\n" +
                " <body>\n" +
                " <h1>Welcome to Spring Boot!</h1>\n" +
                " <a class=\"btn btn-primary\" href=\"https://www.google.com\"> Google </a>\n" +
                " </body>\n" +
                "</html>";
    }
}
