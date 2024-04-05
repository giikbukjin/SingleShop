package com.elice.team4.singleShop.cart.repository;

import com.elice.team4.singleShop.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartIdAndProductId(long Id, long ProductId);
    List<CartItem> findByCartId(Long cartId);
}

