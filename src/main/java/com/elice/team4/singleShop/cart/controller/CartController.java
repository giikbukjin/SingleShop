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

    // 사용자의 장바구니 생성
    @PostMapping("/{id}")
    public String createCart(@PathVariable("id") Long userId) {
        // 사용자 ID를 이용하여 장바구니를 생성하는 메서드를 호출합니다.
        cartService.createCart(userId);
        // 장바구니 화면으로 이동합니다. 임시
        return "redirect:/cart/" + userId + "/view";
    }

    // 장바구니에 상품 추가
    @PostMapping("/{id}/add")
    public String addProductToCart(@PathVariable("id") Long userId,
                                   @RequestParam Long productId,
                                   @RequestParam int count) {
        // 사용자 ID, 상품 ID, 수량을 이용하여 상품을 장바구니에 추가하는 메서드를 호출합니다.
        cartService.addCart(userId, productId, count);
        // 해당 상품의 상세 페이지로 리다이렉션합니다.
        return "redirect:/item/view/" + productId;
    }

    // 사용자의 장바구니 조회
    @GetMapping("/{id}/view")
    public String viewCart(@PathVariable("id") Long userId, Model model) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 조회하는 메서드를 호출합니다.
        List<CartItem> cartItems = cartService.viewCart(userId);
        // 모델에 장바구니 아이템을 추가합니다.
        model.addAttribute("cartItems", cartItems);
        // 장바구니 화면으로 리다이렉션합니다.
        return "redirect:/cart/" + userId + "/view";
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{id}/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable("id") Long userId,
                                 @PathVariable("cartItemId") Long cartItemId) {
        // 사용자 ID와 카트 아이템 ID를 이용하여 장바구니에서 상품을 삭제하는 메서드를 호출합니다.
        cartService.deleteCartItem(cartItemId);
        // 사용자의 정보 페이지로 리다이렉션합니다.
        return "redirect:/cart/" + userId + "/view";
    }

    // 장바구니에서 결제
    @PostMapping("/{id}/checkout")
    public String checkoutCart(@PathVariable("id") Long userId) {
        // 사용자 ID를 이용하여 해당 사용자의 장바구니를 결제하는 메서드를 호출합니다.
        cartService.checkoutCart(userId);
        // 메인 페이지로 리다이렉션합니다.
        return "redirect:/main";
    }
}