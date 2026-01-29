package com.example.spring_mongo_quispe.repository;

import com.example.spring_mongo_quispe.model.PeliculaQuispe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeliculaRepository extends MongoRepository<PeliculaQuispe, String> {

    // Punto 7: Filtrado por género (el nombre del método debe coincidir con el atributo de tu modelo)
    List<PeliculaQuispe> findByGeneroIgnoreCase(String genero);
}