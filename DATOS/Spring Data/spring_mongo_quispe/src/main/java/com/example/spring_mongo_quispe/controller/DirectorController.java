package com.example.spring_mongo_quispe.controller;

import com.example.spring_mongo_quispe.model.DirectorQuispe;
import com.example.spring_mongo_quispe.repository.DirectorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/directores")
public class DirectorController {

    private final DirectorRepository repository;

    public DirectorController(DirectorRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public DirectorQuispe crear(@RequestBody DirectorQuispe director) {
        return repository.save(director);
    }

    // READ (Todos)
    @GetMapping
    public List<DirectorQuispe> listar() {
        return repository.findAll();
    }

    // Punto 7: Endpoint de filtrado por nacionalidad
    @GetMapping("/filtro/{nacionalidad}")
    public List<DirectorQuispe> filtrarPorNacionalidad(@PathVariable String nacionalidad) {
        return repository.findByNacionalidadIgnoreCase(nacionalidad);
    }

    // UPDATE
    @PutMapping("/{id}")
    public DirectorQuispe actualizar(@PathVariable String id, @RequestBody DirectorQuispe nuevo) {
        return repository.findById(id).map(director -> {
            director.setNombre(nuevo.getNombre());
            director.setNacionalidad(nuevo.getNacionalidad());
            director.setEdad(nuevo.getEdad());
            return repository.save(director);
        }).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable String id) {
        repository.deleteById(id);
    }
}