package com.projeto_estoque.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class DAO<E> {
    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected Class<E> entityClass;

    static{
        entityManagerFactory = Persistence
                                .createEntityManagerFactory("estoque");
    }

    public DAO(Class<E> entityClass){
        this.entityManager = entityManagerFactory
                            .createEntityManager();
        this.entityClass = entityClass;
    }

    protected DAO() {
        this(null);
    }

    protected DAO<E> startTransaction(){
        this.entityManager.getTransaction().begin();
        return this;
    }

    protected DAO<E> commitTransaction(){
        this.entityManager.getTransaction().commit();
        return this;
    }

    protected DAO<E> addTransaction(E entity){
        this.entityManager.persist(entity);
        return this;
    }

    protected DAO<E> mergeTransaction(E entity){
        this.entityManager.merge(entity);
        return this;
    }

    protected DAO<E> removeTransaction(E entity){
        this.entityManager.remove(entity);
        return this;
    }

    protected DAO<E> addAtomicTransaction(E entity){
        return this.startTransaction().addTransaction(entity).commitTransaction();
    }

    protected DAO<E> updateAtomicTransaction(E entity){
        return this.startTransaction().mergeTransaction(entity).commitTransaction();
    }

    protected DAO<E> removeAtomicTransaction(E entity){
        return this.startTransaction().removeTransaction(entity).commitTransaction();
    }

    protected void close(){
        this.entityManager.close();
    }
}
