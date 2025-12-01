package com.example.spring_jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class CocheTest {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringJpaApplication.class, args);

        pruebaCoches(context);
    }

    public static void pruebaCoches(ApplicationContext context){
        System.out.println("\n--- PRUEBA DE COCHE REPOSITORIO ---");

        CocheRepository repository = context.getBean(CocheRepository.class);

        System.out.println("Actualmente hay " + repository.count() + " coches en el respostorio");
        Coche coche1 = new Coche(null, "BMW", 2015);
        repository.save(coche1);
        System.out.println("Actualmente hay " + repository.count() + " coches en el respostorio\n");
        System.out.println("Coches recuperados:\n" + repository.findAll() + "\n");
    }
}
