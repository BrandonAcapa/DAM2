package com.example.spring_api;

import com.example.spring_api.dao.CocheDAO;
import com.example.spring_api.entities.Coche;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringApiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringApiApplication.class, args);
        CocheDAO dao = ctx.getBean(CocheDAO.class);
        List<Coche> coches = dao.findAll();
        System.out.println("Coches: " + coches);
	}
}
