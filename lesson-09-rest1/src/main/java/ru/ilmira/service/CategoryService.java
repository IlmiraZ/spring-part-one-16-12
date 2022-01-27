package ru.ilmira.service;

import ru.ilmira.persist.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}