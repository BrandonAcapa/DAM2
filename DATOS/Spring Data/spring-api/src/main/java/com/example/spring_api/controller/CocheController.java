package com.example.spring_api.controller;

import com.example.spring_api.entities.Coche;
import com.example.spring_api.repository.CocheRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CocheController {
    private final CocheRepository repository;

    public CocheController(CocheRepository respository){
        this.repository = respository;
    }

    @GetMapping("/api/coches")
    public List<Coche> mostrarCoches(){
        return repository.findAll();
    }

    @GetMapping("api/coches/{id}")
    public ResponseEntity<Coche> buscarCochePorId(@PathVariable Long id){
        Optional<Coche> opt = repository.findById(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/coches")
    public ResponseEntity<Coche> crearCoche(@RequestBody Coche coche){
        if (coche.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        Coche saved = repository.save(coche);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("api/coches/{id}")
    public ResponseEntity<Coche> modificarCoche(@PathVariable Long id, @RequestBody Coche coche){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        coche.setId(id);
        Coche saved = repository.save(coche);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("api/coches/{id}")
    public ResponseEntity<Coche> eliminarCoche(@PathVariable Long id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
