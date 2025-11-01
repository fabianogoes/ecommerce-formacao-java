package com.ecommerce.repositoty;

import com.ecommerce.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findAllByUserId(Long userId);
    Iterable<Order> findAllByStatus(Order.OrderStatus status);
    Iterable<Order> findAllByStatusAndUserId(Order.OrderStatus status, Long userId);
}