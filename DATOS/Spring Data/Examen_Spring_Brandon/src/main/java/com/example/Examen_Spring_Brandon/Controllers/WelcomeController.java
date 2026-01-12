package com.example.Examen_Spring_Brandon.Controllers;

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
                " <h1>Bienvenido a la biblioteca de Brandon Quispe</h1>\n" +
                " </body>\n" +
                "</html>";
    }
}