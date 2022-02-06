package ru.ilmira.service;

import ru.ilmira.service.dto.CartDto;

import java.util.Optional;

public interface CartService {

    CartDto save(CartDto cartDto);

    Optional<CartDto> findById(Long id);

    void deleteById(Long id);
}
