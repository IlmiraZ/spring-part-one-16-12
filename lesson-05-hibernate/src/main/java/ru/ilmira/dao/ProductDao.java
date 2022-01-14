package ru.ilmira.dao;

import ru.ilmira.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(long id);

    void save(Product product);

}
