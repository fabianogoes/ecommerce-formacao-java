package com.ecommerce.api;

import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.api.dto.ProductUpdatePriceRequestDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repositoty.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRestApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").isNotEmpty())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].category").isString())
                .andExpect(jsonPath("$[0].price").exists())
                .andExpect(jsonPath("$[0].price").isNumber())
                .andExpect(jsonPath("$[0].state").isString())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].description").isString());

    }

    @Test
    void testUpdatePrice() throws Exception {
        // Extrai o conteúdo da resposta como String
        List<ProductDTO> products = getAllProducts();
        Assertions.assertFalse(products.isEmpty());

        ProductDTO productDTO = products.getFirst();
        Long productId = productDTO.id();
        System.out.println(productDTO);

        ProductUpdatePriceRequestDTO productUpdatePriceRequestDTO = new ProductUpdatePriceRequestDTO(11.11);
        String newPriceJson = objectMapper.writeValueAsString(productUpdatePriceRequestDTO);
        mockMvc.perform(
                put("/api/products/"+ productId +"/price")
                        .contentType("application/json")
                        .content(newPriceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.price").value(productUpdatePriceRequestDTO.price()));

        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        Assertions.assertTrue(optionalProduct.isPresent());

        Double productRepositoryPrice = optionalProduct.get().getPrice();
        Assertions.assertEquals(productUpdatePriceRequestDTO.price(), productRepositoryPrice);
    }

    @Test
    void testSoftDeleteProduct() throws Exception {
        // Extrai o conteúdo da resposta como String
        List<ProductDTO> products = getAllProducts();
        Assertions.assertFalse(products.isEmpty());

        ProductDTO productDTO = products.getFirst();
        Long productId = productDTO.id();
        System.out.println(productDTO);

        mockMvc.perform(
                delete("/api/products/" + productId))
                .andExpect(status().isNoContent());

        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        Assertions.assertTrue(optionalProduct.isPresent());

        Product.ProductStatus productStatus = optionalProduct.get().getStatus();
        Assertions.assertEquals(Product.ProductStatus.DELETED, productStatus);
    }

    private List<ProductDTO> getAllProducts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        return objectMapper.readValue(json, new TypeReference<List<ProductDTO>>() {});
    }

}
