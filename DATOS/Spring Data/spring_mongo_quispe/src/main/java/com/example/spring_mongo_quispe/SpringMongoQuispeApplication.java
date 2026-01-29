package com.example.spring_mongo_quispe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMongoQuispeApplication {

    public static void main(String[] args) {
        // Establecemos la URI de Atlas ANTES de que arranque Spring
        System.setProperty("spring.data.mongodb.uri", "mongodb+srv://root:rJBrULeFhvtdk3YQ@cluster0.xnoydpt.mongodb.net/filmoteca_quispe?retryWrites=true&w=majority");

        SpringApplication.run(SpringMongoQuispeApplication.class, args);
    }
}