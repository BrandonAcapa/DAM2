package com.example.spring_mongo_quispe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "pelicula_quispe")
public class PeliculaQuispe {

    @Id
    private String id;
    private String titulo;
    private String genero;
    private int anioEstreno;

    @DBRef
    private DirectorQuispe director;

    public PeliculaQuispe() {
    }

    public PeliculaQuispe(String id, String titulo, String genero, int anioEstreno, DirectorQuispe director) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anioEstreno = anioEstreno;
        this.director = director;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public DirectorQuispe getDirector() {
        return director;
    }

    // En PeliculaQuispe.java, deja el setter así:
    public void setDirector(DirectorQuispe director) {
        this.director = director;
    }

    // Y para permitir que reciba un ID directamente desde el JSON,
// puedes usar un setter alternativo con un nombre diferente para evitar conflictos:
    @JsonProperty("directorId")
    public void setDirectorById(String id) {
        if (id != null) {
            DirectorQuispe d = new DirectorQuispe();
            d.setId(id);
            this.director = d;
        }
    }
}