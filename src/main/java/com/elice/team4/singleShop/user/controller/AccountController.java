package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.dto.UserModifyDto;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public String accountForm(@CookieValue(value = "Authorization") String value, Model model) {
        String token = value.substring(7);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        UserModifyDto userModifyDto = new UserModifyDto(userFindByName);

        model.addAttribute("userInfo", userModifyDto);

        return "account/account";
    }
    @GetMapping("/security/{id}")
    public String accountSecurity(@PathVariable("id") Long id) {
        return "account/account-security";
    }
    @GetMapping("/signout")
    public String accountSignOut() {
        return "account/account-signout";
    }

    @GetMapping("/orders")
    public String accountorders() {
        return "account-orders/account-orders";
    }

}
