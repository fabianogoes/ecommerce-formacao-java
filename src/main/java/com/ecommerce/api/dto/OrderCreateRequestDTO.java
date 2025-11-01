package com.ecommerce.api.dto;

import com.ecommerce.model.Order;

import java.util.List;

public record OrderCreateRequestDTO(
    Long userId,
    Order.PaymentMethod paymentMethod,
    List<OrderItemRequestDTO> items
) {}

