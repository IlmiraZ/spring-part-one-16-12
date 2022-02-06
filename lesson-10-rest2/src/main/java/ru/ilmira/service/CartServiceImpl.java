package ru.ilmira.service;

import org.springframework.stereotype.Service;
import ru.ilmira.persist.*;
import ru.ilmira.service.dto.CartDto;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDto save(CartDto cartDto) {
        Cart cart = new Cart(cartDto.getId());
        Cart saved = cartRepository.save(cart);
        return convertToDto(saved);

    }

    @Override
    public Optional<CartDto> findById(Long id) {
        return cartRepository.findById(id)
                .map(CartServiceImpl::convertToDto);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    private static CartDto convertToDto(Cart cart) {
        return new CartDto(cart.getId());
    }
}









