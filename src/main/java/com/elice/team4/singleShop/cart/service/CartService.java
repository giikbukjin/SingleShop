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
    public void deleteCartItem(Long userId, Long cartItemId) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }

        // 장바구니에서 상품을 삭제합니다.
        cartItemRepository.deleteById(cartItemId);
    }

    // 선택한 상품들 삭제
    public void deleteSelectedItems(@NonNull Long userId, @Nullable List<Long> cartItemIds) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }

        // 선택된 상품들만 삭제합니다.
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

    // 장바구니에서 결제
    @Transactional
    public void cartPayment(Long userId) {
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
        // cartItemRepository.deleteByCart(cart); // 장바구니 비우기 주석 처리
    }

    public Long createOrderFromSelectedItems(Long userId, List<Long> cartItemIds) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회합니다.
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }

        // 선택된 상품들로 주문을 생성합니다.
        List<OrderItem> orderItems = new ArrayList<>();
        for (Long cartItemId : cartItemIds) {
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
            optionalCartItem.ifPresent(cartItem -> {
                if (cartItem.getCart().getUser().getId().equals(userId)) {
                    orderItems.add(OrderItem.createOrderItem(cartItem.getProduct(), cartItem.getCount()));
                }
            });
        }

        // 주문을 생성하고 저장합니다.
        Order order = Order.createOrder(cart.getUser(), orderItems);
        orderRepository.save(order);

        // 주문 ID를 반환합니다.
        return order.getId();
    }
}

