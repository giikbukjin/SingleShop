package com.elice.team4.singleShop.cart.service;

import com.elice.team4.singleShop.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // 사용자 ID를 이용하여 해당 사용자의 장바구니 조회
    public Cart getUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    public List<Product> getUserCartProducts(Cart cart) {
        return cart.getProducts();
    }

    // 사용자의 장바구니에 상품 추가
    public void addProductToCart(User user, Product product, int count) {
        Cart cart = cartRepository.findByUserId(user.getId());

        // 장바구니가 비어있는 경우에만 새로운 장바구니 생성
        if (cart == null) {
            cart = Cart.createCart(user);
        }

        boolean productExistsInCart = cart.getProducts().stream()
                .anyMatch(p -> p.getId().equals(product.getId()));

        if (productExistsInCart) {
            cart.getProducts().stream()
                    .filter(p -> p.getId().equals(product.getId()))
                    .findFirst()
                    .ifPresent(p -> p.addStock(count));
        } else {
            product.setStock(count);
            cart.getProducts().add(product);
        }

        cartRepository.save(cart);
    }

    // 사용자의 장바구니 삭제
    public void deleteCartByUser(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }

    // 사용자의 장바구니 모든 상품 삭제
    public void deleteAllCartItems(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            cart.getProducts().clear();
            cartRepository.save(cart);
        }
    }

    // 사용자가 선택한 상품 결제
    public void checkoutSelectedItems(User user, List<Product> selectedProducts) {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            selectedProducts.forEach(selectedProduct -> cart.getProducts().removeIf(p -> p.getId().equals(selectedProduct.getId())));
            cartRepository.save(cart);
        }
    }
}