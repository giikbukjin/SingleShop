package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistoryDto;
import com.elice.team4.singleShop.order.dto.OrderRequestDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.repository.OrderHistoryRepository;
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
    private final OrderHistoryRepository orderHistoryRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/orderlist")
    @ResponseBody
    public ResponseEntity<List<OrderHistoryDto>> getOrderList() {
        List<OrderHistoryDto> orderHistoryDtoList = orderHistoryRepository.findAll();
        return ResponseEntity.ok(orderHistoryDtoList);
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity deleteStatus(@PathVariable(name = "id") Long id) {
        orderHistoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



}
