package com.example.onlineshop.controller;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal; // Додаємо імпорт

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllProducts() throws Exception {
        // Додай продукт для тесту
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.99"));
        product.setStock(100);
        productService.createProduct(product); // Замінюємо saveProduct на createProduct (або інший метод)

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }
}