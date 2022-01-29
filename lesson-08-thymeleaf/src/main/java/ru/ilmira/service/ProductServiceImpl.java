package ru.ilmira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ilmira.persist.*;
import ru.ilmira.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<String> nameFilter,
                                    Optional<String> minPriceFilter,
                                    Optional<String> maxPriceFilter,
                                    Integer page,
                                    Integer size,
                                    Sort sort) {
        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minPriceFilter.isPresent() && !minPriceFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.minPriceFilter(new BigDecimal(minPriceFilter.get())));
        }
        if (maxPriceFilter.isPresent() && !maxPriceFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.maxPriceFilter(new BigDecimal(maxPriceFilter.get())));
        }

        return productRepository.findAll(spec, PageRequest.of(page, size, sort))
                .map(ProductServiceImpl::convertToDto);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductServiceImpl::convertToDto);
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElse(null);
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(), category);
        Product saved = productRepository.save(product);
        return convertToDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    private static ProductDto convertToDto(Product prod) {
        return new ProductDto(
                prod.getId(),
                prod.getName(),
                prod.getDescription(),
                prod.getPrice(),
                prod.getCategory() != null ? prod.getCategory().getId() : null,
                prod.getCategory() != null ? prod.getCategory().getName() : null
        );
    }
}
