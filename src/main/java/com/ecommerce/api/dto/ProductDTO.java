package com.ecommerce.api.dto;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

public record ProductDTO(
        Long id,
        String name,
        Category category,
        Double price,
        String state,
        String description
) {

    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStatus().name(),
                product.getDescription()
        );
    }
}
