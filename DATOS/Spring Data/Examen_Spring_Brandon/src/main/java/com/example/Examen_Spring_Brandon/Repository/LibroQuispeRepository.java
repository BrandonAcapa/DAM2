package com.example.Examen_Spring_Brandon.Repository;

import com.example.Examen_Spring_Brandon.Entities.LibroQuispe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroQuispeRepository extends JpaRepository<LibroQuispe, Long> {
}
