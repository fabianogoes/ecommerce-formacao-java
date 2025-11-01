package com.ecommerce.api;

import com.ecommerce.api.dto.OrderCreateRequestDTO;
import com.ecommerce.api.dto.OrderResponseDTO;
import com.ecommerce.api.dto.TotalSalesDTO;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderApiRest {

    @Autowired
    OrderService orderService;

    @GetMapping("/total-sales")
    public TotalSalesDTO totalSales() {
        return TotalSalesDTO.fromTotalSales(orderService.totalSales());
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO request) {
        Order order = orderService.createOrderFromApi(request);
        OrderResponseDTO response = OrderResponseDTO.fromOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
