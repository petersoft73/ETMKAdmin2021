package com.petersoft.mgl.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum EntityManagerHandler {

    INSTANCE;

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("mgl");

    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction entityTransaction = entityManager.getTransaction();


    public void open() {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
    }

    public void shutdown() {

        entityTransaction.rollback();
        entityManager.close();
        entityManagerFactory.close();

    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }
}