package ru.ilmira.dao;

import ru.ilmira.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public class ProductDaoImpl implements ProductDao {


    private final EntityManagerFactory emFactory;

    public ProductDaoImpl(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return executeForEntityManager(
                em -> Optional.ofNullable(em.find(Product.class, id))
        );
    }

    @Override
    public List<Product> findAll() {
        return executeForEntityManager(
                em -> em.createQuery("from Product p", Product.class)
                        .getResultList()
        );
    }

    @Override
    public void save(Product product) {
        executeInTransaction(em -> {
            if (product.getId() == null) {
                em.persist(product);
            } else {
                em.merge(product);
            }
        });
    }

    @Override
    public void deleteById(long id) {
        executeInTransaction(
                em -> em.createQuery("delete from Product where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    private <T> T executeForEntityManager(Function<EntityManager, T> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
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
