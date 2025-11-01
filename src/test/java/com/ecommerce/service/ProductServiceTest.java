package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repositoty.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product productBeforeUpdate;
    private final long productId = 1L;
    private final double priceAfterUpdate = 99.99;
    private final double priceBeforeUpdate = 3500.00;

    @BeforeEach
    void setUp() {
        productBeforeUpdate = new Product();
        productBeforeUpdate.setId(productId);
        productBeforeUpdate.setName("Notebook Dell");
        productBeforeUpdate.setPrice(priceBeforeUpdate);
    }

    @Test
    void testUpdatePrice() {
        Product productSaved = new Product();
        productSaved.setPrice(priceAfterUpdate);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(any(Product.class))).thenReturn(productSaved);

        Optional<Product> product = productService.findById(productId);
        Assertions.assertTrue(product.isPresent());
        Assertions.assertNotNull(product.get());
        Assertions.assertEquals(priceBeforeUpdate, product.get().getPrice());

        Product productAfter = productService.updatePrice(productId, priceAfterUpdate);
        Assertions.assertEquals(priceAfterUpdate, productAfter.getPrice());

        verify(productRepository, times(2)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdatePrice_ProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            productService.updatePrice(productId, priceAfterUpdate);
        });

        Assertions.assertEquals("Product not found with id: " + productId, exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testSoftDelete() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(productBeforeUpdate.delete())).thenReturn(productBeforeUpdate.delete());

        productService.deleteById(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(productBeforeUpdate.delete());
    }

}
