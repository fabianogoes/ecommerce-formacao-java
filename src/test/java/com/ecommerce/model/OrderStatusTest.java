package com.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderStatusTest {

    @Test
    void testUserStatusDescription() {
        Assertions.assertEquals("Pendente", Order.OrderStatus.PENDING.getDescription());
        Assertions.assertEquals("Confirmado", Order.OrderStatus.CONFIRMED.getDescription());
        Assertions.assertEquals("Pago", Order.OrderStatus.PAID.getDescription());
        Assertions.assertEquals("Enviado", Order.OrderStatus.SHIPPED.getDescription());
        Assertions.assertEquals("Entregue", Order.OrderStatus.DELIVERED.getDescription());
        Assertions.assertEquals("Cancelado", Order.OrderStatus.CANCELLED.getDescription());
    }

}
