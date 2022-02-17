package ru.ilmira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.ilmira.service.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<String> nameFilter,
                             Optional<String> minPriceFilter,
                             Optional<String> maxPriceFilter,
                             Integer page,
                             Integer size,
                             Sort sort);

    Optional<ProductDto> findById(Long id);

    ProductDto save(ProductDto productDto);

    void deleteById(Long id);
}
