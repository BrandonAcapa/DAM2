package com.example.spring_api.dao;

import com.example.spring_api.entities.Coche;
import java.util.List;

public interface CocheDAO {
    List<Coche> findAll();
}