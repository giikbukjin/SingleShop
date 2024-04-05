package com.elice.team4.singleShop.cart.controller;

import org.springframework.ui.Model;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.service.CartService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.product.service.ProductService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    // 사용자의 장바구니 생성
    @PostMapping("/{id}")
    public String createCart(@PathVariable("id") Long userId) {
        User user = userRepository.findById(userId).orElse(null); // 사용자 아이디로부터 사용자를 조회
        if (user == null) {
            return "redirect:/error"; // 사용자가 없는 경우 에러 페이지로 리다이렉션
        }
        cartService.createCart(user); // 사용자를 이용하여 카트 생성
        return "redirect:/main";
    }
    // 장바구니에 상품 추가
    @PostMapping("/{id}/add")
    public String addProductToCart(@PathVariable("id") Long userId,
                                   @RequestParam Long productId,
                                   @RequestParam int count) {
        User user = userRepository.findById(userId).orElse(null); // 사용자 아이디로부터 사용자를 조회
        Product product = productRepository.findById(productId).orElse(null); // 상품 아이디로부터 상품을 조회
        if (user == null || product == null) {
            return "redirect:/error"; // 사용자나 상품이 없는 경우 에러 페이지로 리다이렉션
        }
        cartService.addCart(user, product, count); // 사용자와 상품 정보를 이용하여 장바구니에 상품 추가
        return "redirect:/item/view/" + productId;
    }

    // 사용자의 장바구니 조회
    @GetMapping("/{id}/view")
    public String viewCart(@PathVariable("id") Long userId, Model model) {
        Cart cart = cartService.findByUserId(userId);
        List<CartItem> cartItems = cartService.userCartView(cart);
        model.addAttribute("cartItems", cartItems);
        return "/cart/view";
    }



    @DeleteMapping("/{id}/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable("id") Long userId, @PathVariable("cartItemId") Long cartItemId) {
        cartService.cartItemDelete(cartItemId);
        return "redirect:/cart/user/" + userId + "/cart";
    }

    // 장바구니에서 결제
    @PostMapping("/{id}/checkout")
    public String checkoutCart(@PathVariable("id") Long userId) {
        cartService.cartPayment(userId);
        return "redirect:/main";
    }
}