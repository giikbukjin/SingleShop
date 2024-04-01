package com.elice.team4.singleShop.cart.controller;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.service.CartService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.service.ProductService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService,ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable("userId") Long userId, Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 인증되어 있는지 확인
        if (authentication != null && authentication.isAuthenticated()) {
            // 사용자 정보에서 사용자 이름 가져오기
            String loggedInUsername = authentication.getName();

            // 여기서 사용자 정보를 이용하여 필요한 작업 수행
            // 예를 들어, 사용자의 장바구니 정보를 가져오는 등의 작업을 수행할 수 있습니다.

            // 장바구니 정보를 가져오는 코드
            Cart cart = cartService.getUserCart(userId);

            // 장바구니에 있는 상품 정보 가져오기
            List<Product> cartProducts = cartService.getUserCartProducts(cart);

            // 장바구니에 있는 상품 총 가격 계산
            int totalPrice = calculateTotalPrice(cartProducts);

            // 모델에 데이터 추가
            model.addAttribute("cartProducts", cartProducts);
            model.addAttribute("totalPrice", totalPrice);

            return "cart/viewCart";
        }

        // 로그인되지 않았거나 인증되지 않은 경우 메인 페이지로 리다이렉트
        return "redirect:/";
    }

    // 장바구니에 있는 상품의 총 가격 계산
    private int calculateTotalPrice(List<Product> cartProducts) {
        int totalPrice = 0;
        for (Product product : cartProducts) {
            totalPrice += (product.getPrice() * product.getStock());
        }
        return totalPrice;
    }


    // 장바구니에 상품 추가하는 메서드
    @PostMapping("/{userId}/add")
    public String addToCart(@PathVariable("userId") Long userId, @RequestParam("productId") Long productId) {
        // 사용자 ID를 이용하여 해당 사용자 엔티티 객체를 조회
        User user = userService.findById(userId).orElse(null);

        // 상품 ID를 이용하여 해당 상품 엔티티 객체를 조회
        Product product = new Product();
        product.setId(productId); // 상품 ID 설정

        if (user != null) {
            // 장바구니에 상품 추가
            cartService.addProductToCart(user, product, 1); // 예시로 수량은 1로 고정하였습니다.

            // 장바구니 페이지로 리다이렉트
            return "redirect:/cart/" + userId;
        } else {
            // 사용자가 존재하지 않는 경우에 대한 처리
            return "redirect:/";
        }
    }
}