package com.elice.team4.singleShop.cart.controller;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // 장바구니에 상품 추가
    @PostMapping("/cart/{id}/add")
    public String addProductToCart(@PathVariable("id") Long userId,
                                   @RequestParam("productId") Long productId,
                                   @RequestParam("count") int count,
                                   RedirectAttributes redirectAttributes) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 이름 가져오기
        User user = userRepository.getByName(username); // UserRepository를 사용하여 사용자 정보 가져오기

        // 장바구니에 상품 추가
        cartService.addCart(userId, productId, count);

        // 장바구니 추가 메시지를 리다이렉트 시에 전달
        redirectAttributes.addFlashAttribute("message", "상품이 장바구니에 추가되었습니다.");

        // 상품 상세 페이지로 리다이렉트
        return "redirect:/products/" + productId;
    }
    // 장바구니 보기
    @GetMapping("/cart")
    public String viewCart(Model model) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 로그인하지 않은 경우 로그인 페이지로 리디렉션
            return "redirect:/auth/login";
        }

        String username = authentication.getName(); // 현재 사용자의 이름 가져오기
        User user = userRepository.getByName(username); // UserRepository를 사용하여 사용자 정보 가져오기

        // 현재 사용자의 ID로 장바구니 조회
        List<CartItem> cartItems = cartService.viewCart(user.getId());

        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getCount();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItemList", cartItems);
        return "cart/cart";
    }

    // 장바구니에서 상품 삭제
    @GetMapping("/cart/{cartItemId}/delete") //html로 구현하기위해 일단 겟 매핑으로 수정
    public String deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 이름 가져오기
        User user = userRepository.getByName(username); // UserRepository를 사용하여 사용자 정보 가져오기

        // 현재 사용자의 ID로 장바구니에서 상품 삭제
        cartService.deleteCartItem(user.getId(), cartItemId);
        return "redirect:/cart";
    }

    // 장바구니에서 선택한 상품 전체 삭제
    @PostMapping("/cart/SelectedItems")
    public String deleteSelectedItems(@RequestBody List<Long> cartItemIds) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 이름 가져오기
        User user = userRepository.getByName(username); // UserRepository를 사용하여 사용자 정보 가져오기

        // 현재 사용자의 ID로 장바구니에서 선택된 상품들 삭제
        cartService.deleteSelectedItems(user.getId(), cartItemIds);
        return "redirect:/cart";
    }

    @PostMapping("/user/{id}/cart/checkout")
    public String myCartPayment(@PathVariable("id") Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getByName(username);

        // 결제처리 및 장바구니 비우기
        cartService.cartPayment(id);

        return "redirect:/order";
    }
/*
    // 선택한 상품들로 구매하기
    @PostMapping("/cart/buy")
    public String createOrderFromSelectedItems(@RequestBody List<Long> cartItemIds) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getByName(username);

        // 선택된 카트 아이템들로 주문 생성
        Long orderId = cartService.createOrderFromSelectedItems(user.getId(), cartItemIds);

        // 생성된 주문 ID를 반환하거나 적절한 처리를 수행
        return "redirect:/order/" + orderId;
    }
 */
}

