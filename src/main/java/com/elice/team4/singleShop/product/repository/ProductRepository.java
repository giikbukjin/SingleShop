package com.elice.team4.singleShop.product.repository;

import com.elice.team4.singleShop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findAllByProductId(Long id);
}
