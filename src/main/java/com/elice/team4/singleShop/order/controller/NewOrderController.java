package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderRequestDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewOrderController {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @DeleteMapping("/orders")
    public String validateOrder(@PathVariable Long orderId, String email) {
        // 주문 내역 삭제하고 결과 받아옴
        boolean deleted = orderService.validateOrder(orderId, email);
        if (deleted) {
            // 주문 내역 삭제 성공 시
            return "redirect:/account/orders"; // 주문 내역 페이지로 리다이렉트
        } else {
            // 주문 내역 존재하지 않거나 삭제 실패 시
            return "error"; // 에러 페이지로 리다이렉트
        }
    }


}
