package com.elice.team4.singleShop.cart.service;

import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.entity.OrderItem;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.repository.UserRepository;
import io.micrometer.common.lang.Nullable;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.user.entity.User;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    // 장바구니에 상품 추가
// Service
    @Transactional
    public void addCart(Long userId, Long productId, int count) {

        User user = userRepository.findById(userId).orElse(null);

        Product product = productRepository.findById(productId).orElse(null);

        if (user == null || product == null) {

            throw new IllegalArgumentException("Invalid user or product");
        }


        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {

            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }


        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItem == null) {

            cartItem = CartItem.createCartItem(cart, product, count);
        } else {

            cartItem.addCount(count);
        }
        cartItemRepository.save(cartItem);
    }


    @Transactional(readOnly = true)
    public List<CartItem> viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cartItemRepository.findByCart(cart);
    }


    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }


        cartItemRepository.deleteById(cartItemId);
    }


    public void deleteSelectedItems(@NonNull Long userId, @Nullable List<Long> cartItemIds) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }


        if (cartItemIds != null) {
            for (Long itemId : cartItemIds) {
                if (itemId != null) {
                    Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
                    optionalCartItem.ifPresent(cartItem -> {
                        if (cartItem.getCart().getUser().getId().equals(userId)) {
                            cartItemRepository.delete(cartItem); // 삭제만 수행
                        }
                    });
                }
            }
        }
    }


    @Transactional
    public void cartPaymentAndClear(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        for (CartItem cartItem : cartItems) {
            int stock = cartItem.getProduct().getStock();
            if (stock < cartItem.getCount()) {
                throw new NotEnoughStockException("Not enough stock for product: " + cartItem.getProduct().getName());
            }
            stock -= cartItem.getCount();
            cartItem.getProduct().setStock(stock);
        }

        // 장바구니 비우기
        cartItemRepository.deleteByCart(cart);
    }
/*
    public Long createOrderFromSelectedItems(Long userId, List<Long> cartItemIds) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }


        List<OrderItem> orderItems = new ArrayList<>();
        for (Long cartItemId : cartItemIds) {
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
            optionalCartItem.ifPresent(cartItem -> {
                if (cartItem.getCart().getUser().getId().equals(userId)) {
                    orderItems.add(OrderItem.createOrderItem(cartItem.getProduct(), cartItem.getCount()));
                }
            });
        }


        Order order = Order.createOrder(cart.getUser(), orderItems);
        orderRepository.save(order);


        return order.getId();
    }*/
}
