package com.elice.team4.singleShop.cart.service;

import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.user.entity.User;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 사용자의 장바구니 생성
    @Transactional
    public void createCart(Long userId) {
        User user = userRepository.findById(userId).orElse(null); // 사용자 아이디로부터 사용자를 조회
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        Cart cart = Cart.createCart(user);
        cartRepository.save(cart);
    }

    // 장바구니에 상품 추가
// Service
    @Transactional
    public void addCart(Long userId, Long productId, int count) {
        // 사용자 ID로 사용자를 조회합니다.
        User user = userRepository.findById(userId).orElse(null);
        // 상품 ID로 상품을 조회합니다.
        Product product = productRepository.findById(productId).orElse(null);

        if (user == null || product == null) {
            // 사용자나 상품이 존재하지 않으면 예외 처리 또는 적절한 오류 처리를 합니다.
            throw new IllegalArgumentException("Invalid user or product");
        }

        // 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            // 사용자의 장바구니가 없으면 생성합니다.
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // 장바구니에 상품을 추가합니다.
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItem == null) {
            // 장바구니에 해당 상품이 없으면 새로운 CartItem을 생성하여 추가합니다.
            cartItem = CartItem.createCartItem(cart, product, count);
        } else {
            // 장바구니에 이미 해당 상품이 있으면 수량을 증가시킵니다.
            cartItem.addCount(count);
        }
        cartItemRepository.save(cartItem);
    }

    // 사용자의 장바구니 조회
    @Transactional(readOnly = true)
    public List<CartItem> viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cartItemRepository.findByCart(cart);
    }

    // 장바구니에서 상품 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 장바구니에서 결제
    @Transactional
    public void checkoutCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        for (CartItem cartItem : cartItems) {
            int stock = cartItem.getProduct().getStock();
            if (stock < cartItem.getCount()) {
                throw new NotEnoughStockException("Not enough stock for product: " + cartItem.getProduct().getName());
            }
            stock -= cartItem.getCount();
            cartItem.getProduct().setStock(stock);
        }
        // 결제 후 장바구니 비우기
        cartItemRepository.deleteByCart(cart);
    }
}