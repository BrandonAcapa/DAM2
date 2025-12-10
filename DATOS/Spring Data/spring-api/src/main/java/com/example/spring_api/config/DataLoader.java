package com.example.spring_api.config;

import com.example.spring_api.entities.Coche;
import com.example.spring_api.repository.CocheRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final CocheRepository repository;

    public DataLoader(CocheRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(new Coche(null, "BMW", 2015));
        repository.save(new Coche(null, "Mercedes", 2020));
    }
}
