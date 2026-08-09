package com.yaswanth.ecommerce.product.service;

import com.yaswanth.ecommerce.product.dto.ProductRequest;
import com.yaswanth.ecommerce.product.dto.ProductResponse;
import com.yaswanth.ecommerce.product.entity.Product;
import com.yaswanth.ecommerce.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductRepository productRepository;

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToProductResponse)
                .toList();
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        return convertProductToProductResponse(
                productRepository.save(
                        convertProductRequestToProduct(productRequest)));
    }

    private ProductResponse convertProductToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCurrency());
    }

    private Product convertProductRequestToProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getCurrency(),
                productRequest.getDescription(),
                productRequest.getAvailableQuantity());
    }
}
