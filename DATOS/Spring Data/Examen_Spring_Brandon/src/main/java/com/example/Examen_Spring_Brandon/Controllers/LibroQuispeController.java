// java
package com.example.Examen_Spring_Brandon.Controllers;

import com.example.Examen_Spring_Brandon.Entities.LibroQuispe;
import com.example.Examen_Spring_Brandon.Repository.LibroQuispeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros_quispe")
public class LibroQuispeController {

    private final LibroQuispeRepository repo;

    @Autowired
    public LibroQuispeController(LibroQuispeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<LibroQuispe> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroQuispe> findById(@PathVariable Long id) {
        Optional<LibroQuispe> opt = repo.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LibroQuispe> create(@RequestBody LibroQuispe libro) {
        LibroQuispe saved = repo.save(libro);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroQuispe> update(@PathVariable Long id, @RequestBody LibroQuispe libro) {
        return repo.findById(id).map(existing -> {
            // Actualizar solo si el campo recibido no es null (evita sobrescribir con null)
            if (libro.getTitulo() != null) {
                existing.setTitulo(libro.getTitulo());
            }
            if (libro.getAutor() != null) {
                existing.setAutor(libro.getAutor());
            }
            if (libro.getAnyoPublicacion() != null) {
                existing.setAnyoPublicacion(libro.getAnyoPublicacion());
            }
            LibroQuispe updated = repo.save(existing);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
