package ru.ilmira.dao;

import org.springframework.stereotype.Component;
import ru.ilmira.entity.Client;
import ru.ilmira.entity.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao, Util {

    private final EntityManagerFactory emFactory;

    public UserDaoImpl(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<User> findAll() {
        return executeForEntityManager(emFactory,
                em -> em.createQuery("from User u", User.class)
                        .getResultList()
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return executeForEntityManager(emFactory,
                em -> Optional.ofNullable(em.find(User.class, id))
        );
    }

    @Override
    public void deleteById(Long id) {
        executeInTransaction(emFactory,
                em -> em.createQuery("delete from User where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    @Override
    public void save(Client client) {
        executeInTransaction(emFactory, em -> {
            if (client.getId() == null) {
                em.persist(client);
            } else {
                em.merge(client);
            }
        });
    }
}
