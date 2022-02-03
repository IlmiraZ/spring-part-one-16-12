package ru.ilmira.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ilmira.controller.NotFoundException;
import ru.ilmira.persist.Cart;
import ru.ilmira.persist.CartRepository;
import ru.ilmira.service.CartService;
import ru.ilmira.service.ProductService;
import ru.ilmira.service.dto.CartDto;
import ru.ilmira.service.dto.OrderItemDto;
import ru.ilmira.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {
    private final CartService cartService;
    private final ProductService productService;

    public CartResource(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public CartDto find(@PathVariable("id") Long id) {
        return cartService.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto create() {
        return cartService.save(new CartDto());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {    // пока без проверок
        cartService.deleteById(id);
    }

    @PutMapping("/{id}")
    public CartDto update(@PathVariable("id") Long id,
                          @RequestBody Set<OrderItemDto> orderItemDto) {
        CartDto cartDto = cartService.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found"));

        orderItemDto = orderItemDto.stream()
                .filter(item -> item.getQuantity() == null || item.getQuantity() > 0)
                .collect(Collectors.toSet());

        orderItemDto.forEach(orderItem -> {
            Long productId = orderItem.getProduct().getId();

            ProductDto productDto = productService.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));

            if (orderItem.getPrice() == null || orderItem.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                orderItem.setPrice(productDto.getPrice());
            }
            if (orderItem.getQuantity() == null) {
                orderItem.setQuantity(1);
            }
            orderItem.setCartId(id);
        });

        cartDto.setOrderItemDto(orderItemDto);

        return cartService.save(cartDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgumentException(IllegalArgumentException ex) {
        return new ErrorDto(ex.getMessage());
    }
}
