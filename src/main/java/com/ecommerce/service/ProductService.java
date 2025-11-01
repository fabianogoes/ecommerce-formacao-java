package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repositoty.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getAllProducts(Category category) {
        System.out.println("finding products for category: " + category);

        Iterable<Product> products;
        if (category != null) {
            products = productRepository.findByCategoryAndStatus(category, Product.ProductStatus.CREATED);
        } else {
            products = productRepository.findAllByStatus(Product.ProductStatus.CREATED);
        }

        return products;
    }

    public Product save(Product product) {
        System.out.println("Saving product: " + product);
        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        System.out.println("Finding product by ID: " + id);
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        System.out.println("Deleting product by ID: " + id);

        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.save(product.delete());
    }

    public long count() {
        return productRepository.count();
    }

    public Product updatePrice(Long id, Double price) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPrice(price);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
