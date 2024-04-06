package com.elice.team4.singleShop.product.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // ProductMapper 의존성 주입
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    // 제품 저장
    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto); // DTO를 엔티티로 변환
        return productRepository.save(product);
    }

    // 모든 제품 조회
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 아이디로 제품 조회
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // 제품 정보 수정
    @Transactional
    public void updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
        productMapper.updateProductFromDto(productDto, product);
        productRepository.save(product);
    }


    // 카테고리 서비스에서 모든 카테고리 조회
    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findCategories();
    }
}
