package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
