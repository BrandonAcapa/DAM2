package com.example.Examen_Spring_Brandon.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "libro_quispe")
public class LibroQuispe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "anyo_publicacion")
    private Integer anyoPublicacion;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnyoPublicacion() {
        return anyoPublicacion;
    }

    public void setAnyoPublicacion(Integer anyoPublicacion) {
        this.anyoPublicacion = anyoPublicacion;
    }
}
