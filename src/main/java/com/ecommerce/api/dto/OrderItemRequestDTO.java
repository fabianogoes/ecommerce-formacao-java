package com.ecommerce.api.dto;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity
) {}
