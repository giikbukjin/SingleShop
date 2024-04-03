package com.elice.team4.singleShop.product.service;

import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // ProductMapper 의존성 주입

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // 제품 저장 또는 업데이트
    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto); // DTO를 엔티티로 변환
        return productRepository.save(product);
    }
}
