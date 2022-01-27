package ru.ilmira.service;

import ru.ilmira.persist.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
}