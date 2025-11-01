package com.ecommerce.api.dto;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
    Long id,
    Long userId,
    Double totalValue,
    Integer totalItems,
    String status,
    String paymentMethod,
    LocalDateTime createdAt,
    List<OrderItemResponseDTO> items
) {
    public static OrderResponseDTO fromOrder(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getTotalValue(),
                order.getTotalItems(),
                order.getStatus().name(),
                order.getPaymentMethod().getDescription(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(OrderItemResponseDTO::fromOrderItem)
                        .toList()
        );
    }
}

record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        Double unitPrice,
        Double subtotal
) {
    public static OrderItemResponseDTO fromOrderItem(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }
}