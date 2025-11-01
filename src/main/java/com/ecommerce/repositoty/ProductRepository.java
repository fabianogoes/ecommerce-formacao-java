package com.ecommerce.repositoty;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findAllByStatus(Product.ProductStatus status);
    Iterable<Product> findByCategoryAndStatus(Category category, Product.ProductStatus status);
}
