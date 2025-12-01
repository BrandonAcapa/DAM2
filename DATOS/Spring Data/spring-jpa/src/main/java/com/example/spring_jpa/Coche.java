package com.example.spring_jpa;

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

    public long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", anyo=" + anyo +
                '}';
    }
}
