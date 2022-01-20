package ru.ilmira.dao;

import ru.ilmira.entity.Client;
import ru.ilmira.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();

    Optional<User> findById(Long id);

    void deleteById(Long id);

    void save(Client client);
}
