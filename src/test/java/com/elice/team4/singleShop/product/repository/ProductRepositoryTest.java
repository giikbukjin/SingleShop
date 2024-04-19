package com.elice.team4.singleShop.product.repository;

import com.elice.team4.singleShop.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest // JPA 컴포넌트만 로드
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 데이터베이스 사용
@Rollback(value = true)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testProduct() {
        // given : 상품 생성 및 저장
        Product product = new Product();
        product.setName("productA");
        Product savedProduct = productRepository.save(product);

        // when : 저장된 상품 id로 상품 조회
        Optional<Product> findProduct = productRepository.findById(savedProduct.getId());

        // then : 조회된 상품이 존재하는지 확인
        assertTrue(findProduct.isPresent()); // 상품이 정상적으로 조회되었는지 확인
        assertEquals("productA", findProduct.get().getName()); // 상품명이 일치하는지 확인
    }
}