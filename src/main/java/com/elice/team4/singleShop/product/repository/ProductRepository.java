package com.elice.team4.singleShop.product.repository;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    // 상품 이름으로 상품 조회
    List<Product> findByName(String name);

    // Category Paging
    Page<Product> findAllByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);
    Page<Product> findAllByCategoryAndNameContaining(Category category, String keyword, Pageable pageable);
}
