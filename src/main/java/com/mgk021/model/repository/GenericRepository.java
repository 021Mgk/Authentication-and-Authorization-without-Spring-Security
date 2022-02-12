package com.mgk021.model.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenericRepository<I extends Number, T , S extends String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(T t) {
        entityManager.persist(t);
    }

    @Transactional
    public void update(T t) {
        entityManager.merge(t);
    }

    @Transactional
    public void delete(T t) {
        t = entityManager.merge(t);
        entityManager.remove(t);
    }

    public T findOne(Class aClass, I i) {
        return (T) entityManager.find(aClass, i);
    }

    public List<T> findAll(Class aClass) {
        return entityManager.createQuery("select o from " + ((Entity) aClass.getAnnotation(Entity.class)).name() + " o").getResultList();
    }

    public List<T> findByWhere(Class aClass , S s) {
        return  entityManager.createQuery("select o from " + ((Entity) aClass.getAnnotation(Entity.class)).name() + " o where " + s).getResultList();
    }

}