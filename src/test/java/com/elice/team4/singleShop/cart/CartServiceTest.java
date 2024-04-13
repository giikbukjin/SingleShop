package com.elice.team4.singleShop.cart;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.cart.service.CartService;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {/*

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartService cartService;

    @Test
    void createCartTest() {
        // Given
        User user = new User();
        Cart cart = new Cart();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        cartService.createCart(1L);

        // Then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addCartTest() {
        // Given
        Long userId = 1L;
        Long productId = 123L;
        int count = 1;
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setId(1L);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(null);

        // When
        cartService.addCart(userId, productId, count);

        // Then
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void viewCartTest() {
        // Given
        Long userId = 1L;
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        // When
        List<CartItem> result = cartService.viewCart(userId);

        // Then
        assertEquals(cartItems, result);
    }

    @Test
    void deleteCartItemTest() {
        // Given
        Long cartItemId = 123L;

        // When
        cartService.deleteCartItem(cartItemId);

        // Then
        verify(cartItemRepository, times(1)).deleteById(cartItemId);
    }

    @Test
    void checkoutCartTest() {
        // Given
        Long userId = 1L;
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product = new Product();
        product.setId(123L);
        product.setStock(10);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCount(5);
        cartItems.add(cartItem);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        // When
        cartService.checkoutCart(userId);

        // Then
        verify(cartItemRepository, times(1)).deleteByCart(cart);
        assertEquals(5, product.getStock());
    }

    @Test
    void checkoutCartTest_NotEnoughStockException() {
        // Given
        Long userId = 1L;
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product = new Product();
        product.setId(123L);
        product.setStock(5); // setting lower stock than required
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCount(10); // setting higher count than available stock
        cartItems.add(cartItem);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        // When, Then
        assertThrows(NotEnoughStockException.class, () -> cartService.checkoutCart(userId));
    }
*/}