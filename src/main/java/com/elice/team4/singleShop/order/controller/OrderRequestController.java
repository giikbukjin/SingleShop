package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderRequestDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OrderRequestController {

    private final OrderRepository orderRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/order")
    public ResponseEntity<OrderRequestDto> getOrder(@RequestBody OrderRequestDto orderRequestDto,
                                                    @CookieValue(value = "Authorization") String value) {
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        Order newOrder = new Order(orderRequestDto);
        newOrder.setEmail(userFindByName.getEmail());

        newOrder.setAddress(orderRequestDto.mapToString(orderRequestDto.getAddress()));

        orderRepository.save(newOrder);

        OrderRequestDto orderRequestDto1 = new OrderRequestDto(newOrder);

        return ResponseEntity.ok(orderRequestDto1);
    }
}