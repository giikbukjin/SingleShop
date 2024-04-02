package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
}
