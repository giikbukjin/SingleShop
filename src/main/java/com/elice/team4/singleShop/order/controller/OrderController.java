package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.service.CartService;
import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.entity.OrderItem;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import com.elice.team4.singleShop.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/order/create")
    public String createOrder(@CookieValue(value = "Authorization") String value, Model model) {
        // 현재 사용자 정보 가져오기
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        // 장바구니에서 주문에 포함될 상품들 가져오기
        List<CartItem> cartItems = cartService.viewCart(userFindByName.getId());

        // 주문 리스트 생성 및 주문에 상품들 추가
        Order order = new Order(); // 주문 객체 생성
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setCount(cartItem.getCount());
                    orderItem.setOrderPrice(cartItem.getProduct().getPrice()); // 상품의 가격 설정
                    orderItem.setOrder(order); // 주문 아이템에 주문 설정
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems); // 주문에 주문 아이템들 설정
        order.setUser(userFindByName); // 주문에 사용자 설정

        // 주문 저장
        orderRepository.save(order);

        // 주문 페이지로 이동
        return "order/order";
    }

    @GetMapping("/order")
    public String viewOrder(@CookieValue(value = "Authorization") String value, Model model) {
        // 현재 사용자 정보 가져오기
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        log.info("order에서 주문정보가지고 만든 user {}", userFindByName);

        List<CartItem> cartItems = cartService.viewCart(userFindByName.getId());

        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getCount();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItemList", cartItems);

        return "order/order";
    }

    @GetMapping("/order-complete")
    public String orderCompletePage() {
        // 주문 완료 페이지로 이동
        return "order/order-complete"; // order-complete.html에 대한 뷰로 이동
    }

    // 주문 내역 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable(value = "page", required = false) Integer page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, 4);

        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("order", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "account-orders/account-orders";
    }

    // 주문 취소 처리
    @PostMapping("/order/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, Principal principal, Model model) {
        // 주문 취소 권한 검사
        if (!orderService.validateOrder(orderId, principal.getName())) {
            model.addAttribute("errorMessage", "주문 취소 권한이 없습니다.");
            return "error-page"; // 에러 페이지로 이동
        }

        orderService.cancelOrder(orderId); // 주문 취소 로직 호출 -> 처리
        return "redirect:/order"; // 주문 내역 페이지로 리다이렉트
    }
}