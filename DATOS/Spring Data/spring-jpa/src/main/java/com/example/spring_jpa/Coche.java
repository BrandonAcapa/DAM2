package com.example.spring_jpa;

public class Coche {
    long id;
    String modelo;
    int anyo;

    public Coche(){
    }

    public Coche(long id, String modelo, int anyo){
        this.id = id;
        this.modelo = modelo;
        this.anyo = anyo;
    }
}
