package com.example.spring_api;

import com.example.spring_api.entities.Coche;
import com.example.spring_api.repository.CocheRepository;
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

        CocheRepository respository = context.getBean(CocheRepository.class);

        respository.save(coche1);
        respository.save(coche2);
    }
}
