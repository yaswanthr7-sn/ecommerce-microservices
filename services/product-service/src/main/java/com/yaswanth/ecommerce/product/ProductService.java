package com.yaswanth.ecommerce.product;

import com.yaswanth.ecommerce.product.dto.ProductResponse;
import com.yaswanth.ecommerce.product.entity.Product;
import com.yaswanth.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductRepository productRepository;

    public List<ProductResponse> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToProductResponse)
                .toList();
    }

    private ProductResponse convertProductToProductResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getCurrency());
    }
}
