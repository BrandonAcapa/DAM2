package com.example.spring_api.entities;

import jakarta.persistence.*;

@Entity
@Table(name="coches")
public class Coche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String modelo;
    int anyo;

    public Coche(){
    }

    public Coche(Long id, String modelo, int anyo){
        this.id = id;
        this.modelo = modelo;
        this.anyo = anyo;
    }

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }
}
