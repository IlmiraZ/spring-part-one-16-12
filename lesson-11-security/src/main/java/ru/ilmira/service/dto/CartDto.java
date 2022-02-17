package ru.ilmira.service.dto;

import java.util.Set;

public class CartDto {

    private Long id;

    Set<OrderItemDto> orderItemDto;

    public CartDto(Long id) {
        this.id = id;
    }

    public CartDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<OrderItemDto> getOrderItemDto() {
        return orderItemDto;
    }

    public void setOrderItemDto(Set<OrderItemDto> orderItemDto) {
        this.orderItemDto = orderItemDto;
    }
}
