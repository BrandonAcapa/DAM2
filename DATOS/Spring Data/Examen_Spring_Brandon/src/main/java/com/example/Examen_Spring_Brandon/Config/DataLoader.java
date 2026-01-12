package com.example.Examen_Spring_Brandon.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.Examen_Spring_Brandon.Entities.LibroQuispe;
import com.example.Examen_Spring_Brandon.Repository.LibroQuispeRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final LibroQuispeRepository repo;

    public DataLoader(LibroQuispeRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            LibroQuispe libro = new LibroQuispe();
            libro.setTitulo("El Principito");
            libro.setAutor("Antoine de Saint-Exupéry");
            libro.setAnyoPublicacion(1943);
            repo.save(libro);
            System.out.println("Libro insertado: " + libro.getTitulo());
        }
    }
}
