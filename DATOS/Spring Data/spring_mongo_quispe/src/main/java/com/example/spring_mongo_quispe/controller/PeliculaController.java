package com.example.spring_mongo_quispe.controller;

import com.example.spring_mongo_quispe.model.PeliculaQuispe;
import com.example.spring_mongo_quispe.repository.PeliculaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final PeliculaRepository repository;

    public PeliculaController(PeliculaRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public PeliculaQuispe crear(@RequestBody PeliculaQuispe pelicula) {
        return repository.save(pelicula);
    }

    // READ
    @GetMapping
    public List<PeliculaQuispe> listar() {
        return repository.findAll();
    }

    // Punto 7: Endpoint de filtrado por género
    @GetMapping("/filtro/{genero}")
    public List<PeliculaQuispe> filtrarPorGenero(@PathVariable String genero) {
        return repository.findByGeneroIgnoreCase(genero);
    }

    // UPDATE
    @PutMapping("/{id}")
    public PeliculaQuispe actualizar(@PathVariable String id, @RequestBody PeliculaQuispe nueva) {
        return repository.findById(id).map(p -> {
            p.setTitulo(nueva.getTitulo());
            p.setGenero(nueva.getGenero());
            p.setDirector(nueva.getDirector()); // Mantiene la asociación
            return repository.save(p);
        }).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable String id) {
        repository.deleteById(id);
    }
}