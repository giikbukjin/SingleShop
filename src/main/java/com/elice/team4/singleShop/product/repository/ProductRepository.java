package com.elice.team4.singleShop.product.repository;

import com.elice.team4.singleShop.product.domain.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final EntityManager em;

    // 상품 등록
    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product); // 신규 등록
        } else {
            em.merge(product);
        }
    }

    // 상품 조회
    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }
    // 전체 상품 조회
    public List<Product> findAll() {
        return em.createQuery("SELECT i FROM Product i", Product.class)
                .getResultList();
    }
}
