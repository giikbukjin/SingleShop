package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*@PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto,
                                               BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) { // 데이터 바인딩 시 에러 있는지 검사
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); // 에러 정보
        }
        String email = principal.getName(); // 현재 로그인한 회원 이메일 정보 조회
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); // 주문 정보, 회원 이메일 정보로 주문 로직 호출
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK); // 생성된 주문 번호와 요청 성공 HTTP 응답 상태 코드
    }*/
}