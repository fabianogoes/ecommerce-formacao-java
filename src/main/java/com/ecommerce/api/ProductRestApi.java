package com.ecommerce.api;

import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.api.dto.ProductUpdatePriceRequestDTO;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestApi {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        Iterable<Product> products = productService.getAllProducts(null);

        return StreamSupport.stream(products.spliterator(), false)
                .map(ProductDTO::fromProduct)
                .toList();
    }

    @PutMapping("/{id}/price")
    public ProductDTO updatePrice(@PathVariable Long id, @RequestBody ProductUpdatePriceRequestDTO request) {
        Product product = productService.updatePrice(id, request.price());
        return ProductDTO.fromProduct(product);
    }


    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
