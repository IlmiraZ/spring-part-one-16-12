package ru.ilmira.dao;

import ru.ilmira.entity.Product;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;


public class ProductDaoImpl implements ProductDao, Util {

    private final EntityManagerFactory emFactory;

    public ProductDaoImpl(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return executeForEntityManager(emFactory,
                em -> Optional.ofNullable(em.find(Product.class, id))
        );
    }

    @Override
    public List<Product> findAll() {
        return executeForEntityManager(emFactory,
                em -> em.createQuery("from Product p", Product.class)
                        .getResultList()
        );
    }

    @Override
    public void save(Product product) {
        executeInTransaction(emFactory, em -> {
            if (product.getId() == null) {
                em.persist(product);
            } else {
                em.merge(product);
            }
        });
    }

    @Override
    public void deleteById(long id) {
        executeInTransaction(emFactory,
                em -> em.createQuery("delete from Product where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }
}
