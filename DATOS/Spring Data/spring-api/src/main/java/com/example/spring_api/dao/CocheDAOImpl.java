package com.example.spring_api.dao;

import com.example.spring_api.entities.Coche;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CocheDAOImpl implements CocheDAO{
    private Session session; // Hibernate

    public CocheDAOImpl(EntityManager entityManager) {
        this.session = entityManager.unwrap(Session.class);
    }

    @Override
    public List<Coche> findAll() {
        return session.createQuery("from Coche", Coche.class).getResultList();
    }
}
