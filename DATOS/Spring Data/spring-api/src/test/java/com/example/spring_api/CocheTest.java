package com.example.spring_api;

import com.example.spring_api.controller.CocheController;
import com.example.spring_api.entities.Coche;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;

@SpringBootTest
public class CocheTest {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringApiApplication.class, args);

        pruebaCoches(context);
    }

    public static void pruebaCoches(ApplicationContext context){
        Coche coche1 = new Coche(null, "BMW", 2015);
        Coche coche2 = new Coche(null, "Mercedes", 2020);

        CocheController respository = context.getBean(CocheController.class);

        respository.save(coche1);
    }
}
