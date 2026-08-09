package com.yaswanth.ecommerce.product.service;

import com.yaswanth.ecommerce.product.dto.ProductRequest;
import com.yaswanth.ecommerce.product.dto.ProductResponse;
import com.yaswanth.ecommerce.product.entity.Product;
import com.yaswanth.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void getProductsTest() {
        when(productRepository.findAll())
                .thenReturn(List.of());
        List<ProductResponse> result = productService.getProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void createProductTest() {
        ProductRequest productRequest = new ProductRequest("Samsung", new BigDecimal("59999"), "INR", "Test Product", 10);
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ProductResponse result =
                productService.createProduct(productRequest);
        assertEquals("Samsung", result.getName());
        verify(productRepository).save(any(Product.class));
        assertEquals(new BigDecimal("59999"), result.getPrice());
        assertEquals("INR", result.getCurrency());
    }

}
