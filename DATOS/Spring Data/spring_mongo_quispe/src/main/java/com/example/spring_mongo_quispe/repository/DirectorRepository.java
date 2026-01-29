package com.example.spring_mongo_quispe.repository;

import com.example.spring_mongo_quispe.model.DirectorQuispe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DirectorRepository extends MongoRepository<DirectorQuispe, String> {
    // Punto 7: Filtrado por nombre
    List<DirectorQuispe> findByNombreContainingIgnoreCase(String nombre);

    // Punto 7: Filtrado por nacionalidad
    List<DirectorQuispe> findByNacionalidadIgnoreCase(String nacionalidad);
}