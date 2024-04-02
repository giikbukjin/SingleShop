package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final SignService signService;

    public UserController(SignService signService) {
        this.signService = signService;
    }
}
