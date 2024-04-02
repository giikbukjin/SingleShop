package com.elice.team4.singleShop.cart.repository;

import com.elice.team4.singleShop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}