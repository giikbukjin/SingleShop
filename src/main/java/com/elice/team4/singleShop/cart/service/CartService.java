package com.elice.team4.singleShop.cart.service;

import com.elice.team4.singleShop.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void createCart(User user){
        Cart cart = Cart.createCart(user);
        cartRepository.save(cart);
    }
    @Transactional
    public void addCart(User user, int count) {

        Cart cart = cartRepository.findByUserId(user.getId());

        // cart 가 비어있다면 생성.
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }
    }

}