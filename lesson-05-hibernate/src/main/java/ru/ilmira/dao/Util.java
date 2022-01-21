package ru.ilmira.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Util {
    default <T> T executeForEntityManager(EntityManagerFactory emFactory, Function<EntityManager, T> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    default void executeInTransaction(EntityManagerFactory emFactory, Consumer<EntityManager> consumer) {
        EntityManager entityManager = emFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            consumer.accept(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
