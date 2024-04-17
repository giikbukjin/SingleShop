package com.elice.team4.singleShop.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/new")
public class NewOrderController {

    @GetMapping("/order")
    public String getOrderPage() {
        return "/order";
    }

}
