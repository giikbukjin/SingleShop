package com.elice.team4.singleShop.product.service;

import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public Product findOne(Long productId) {
        return productRepository.findOne(productId);
    }
}
