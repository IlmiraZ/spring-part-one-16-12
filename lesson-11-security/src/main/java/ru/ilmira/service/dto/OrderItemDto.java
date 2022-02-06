package ru.ilmira.service.dto;

import java.math.BigDecimal;

public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal price;
    private Long cartId;

    public OrderItemDto(Long id, ProductDto product, Integer quantity, BigDecimal price, Long cartId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.cartId = cartId;
    }

    public OrderItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
