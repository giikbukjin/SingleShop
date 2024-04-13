package com.elice.team4.singleShop.cart;

import com.elice.team4.singleShop.cart.controller.CartController;
import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {/*

    @Test
    void createCartTest() {
        // Given
        Long userId = 1L;
        CartService cartService = mock(CartService.class);
        CartController cartController = new CartController(cartService);

        // When
        String result = cartController.createCart(userId);

        // Then
        assertEquals("redirect:/cart/" + userId + "/view", result); // 수정된 부분
        verify(cartService, times(1)).createCart(userId);
    }

    @Test
    void addProductToCartTest() {
        // Given
        Long userId = 1L;
        Long productId = 123L;
        int count = 2;
        CartService cartService = mock(CartService.class);
        CartController cartController = new CartController(cartService);

        // When
        String result = cartController.addProductToCart(userId, productId, count);

        // Then
        assertEquals("redirect:/item/view/123", result);
        verify(cartService, times(1)).addCart(userId, productId, count);
    }

    @Test
    void viewCartTest() {
        // Given
        Long userId = 1L;
        Model model = mock(Model.class);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());
        CartService cartService = mock(CartService.class);
        when(cartService.viewCart(userId)).thenReturn(cartItems);
        CartController cartController = new CartController(cartService);

        // When
        String result = cartController.viewCart(userId, model);

        // Then
        assertEquals("redirect:/cart/" + userId + "/view", result); // 수정된 부분
        verify(model, times(1)).addAttribute("cartItems", cartItems);
    }

    @Test
    void deleteCartItemTest() {
        // Given
        Long userId = 1L;
        Long cartItemId = 123L;
        CartService cartService = mock(CartService.class);
        CartController cartController = new CartController(cartService);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // When
        String result = cartController.deleteCartItem(userId, cartItemId);

        // Then
        assertEquals("redirect:/cart/" + userId + "/view", result); // 수정된 부분
        verify(cartService, times(1)).deleteCartItem(cartItemId);
    }

    @Test
    void checkoutCartTest() {
        // Given
        Long userId = 1L;
        CartService cartService = mock(CartService.class);
        CartController cartController = new CartController(cartService);

        // When
        String result = cartController.checkoutCart(userId);

        // Then
        assertEquals("redirect:/main", result);
        verify(cartService, times(1)).checkoutCart(userId);
    }
*/}