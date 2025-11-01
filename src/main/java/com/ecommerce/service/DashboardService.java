package com.ecommerce.service;

import com.ecommerce.repositoty.OrderRepository;
import com.ecommerce.repositoty.ProductRepository;
import com.ecommerce.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

}
