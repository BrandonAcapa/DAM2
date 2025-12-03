package com.example.spring_api.controller;

import com.example.spring_api.SpringApiApplication;
import com.example.spring_api.repository.CocheRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CocheController {
    private final CocheRepository repository;

    public CocheController(CocheRepository respository){
        this.repository = respository;
    }

    @GetMapping("/api/coches")
    public String mostrarCoches(){
        return repository.findAll().toString();
    }
}
