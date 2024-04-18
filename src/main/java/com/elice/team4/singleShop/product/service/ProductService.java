package com.elice.team4.singleShop.product.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // ProductMapper 의존성 주입
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    // 제품 저장
    @Transactional
    public Product saveProduct(ProductDto productDto) {
        // 카테고리 ID를 통해 카테고리 엔티티 조회
        Category category = null;
        if (productDto.getCategoryId() != null) {
            category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + productDto.getCategoryId()));
        }

        // DTO를 상품 엔티티로 변환
        Product product = productMapper.productDtoToProduct(productDto);

        // 변환된 상품 엔티티에 카테고리 설정
        product.setCategory(category);

        // 상품 엔티티 저장
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

    // 카테고리 페이지에서 키워드로 제품 조회
    public Page<Product> findProductsByCategoryAndKeyword(Category category, String keyword, PageRequest pageRequest) {
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findAllByCategoryAndNameContaining(category, keyword, pageRequest);
        } else {
            return productRepository.findAllByCategoryOrderByCreatedAtDesc(category, pageRequest);
        }
    }

    // 제품 정보 수정
    @Transactional
    public void updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        // 상품 정보 업데이트
        productMapper.updateProductFromDto(productDto, product);

        // 카테고리 업데이트
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        } else {
            // 카테고리 ID가 null이거나 "없음"으로 선택된 경우
            product.setCategory(null);
        }

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // 상품이 존재하는지 확인하고, 존재한다면 삭제
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
        productRepository.delete(product);
    }
}
