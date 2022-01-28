package ru.ilmira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ilmira.persist.*;
import ru.ilmira.service.dto.CategoryDto;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Page<CategoryDto> findAll(Optional<String> nameFilter,
                                     Integer page,
                                     Integer size,
                                     Sort sort
                                     ) {
        Specification<Category> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(CategorySpecification.nameLike(nameFilter.get()));
        }

        return categoryRepository.findAll(spec, PageRequest.of(page, size, sort))
                .map(CategoryServiceImpl::convertToDto);
    }

    @Override
    public Optional<CategoryDto> findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryServiceImpl::convertToDto);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = new Category(
                categoryDto.getId(),
                categoryDto.getName());
        Category saved = categoryRepository.save(category);
        return convertToDto(saved);

    }

    private static CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getId(),category.getName());
    }
}