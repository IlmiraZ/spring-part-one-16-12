package ru.ilmira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.ilmira.service.dto.CategoryDto;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryDto> findAll(Optional<String> nameFilter,
                              Integer page,
                              Integer size,
                              Sort sort
                              );

    Optional<CategoryDto> findById(Long categoryId);

    void deleteById(Long id);

    CategoryDto save(CategoryDto category);

}