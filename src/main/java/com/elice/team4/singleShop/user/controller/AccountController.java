package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @GetMapping
    public String accountForm() {
        return "account/account";
    }
    @GetMapping("/security")
    public String accountSecurity() {
        return "account/account-security";
    }
    @GetMapping("/signout")
    public String accountSignOut() {
        return "account/account-signout";
    }

    // TODO : JS 고쳐지면, 비밀번호를 받아서, 리턴 값을 준 다음에 삭제 시킬 유저를 반납한다.
//    @ResponseBody
//    @PostMapping("users/password-check")
//    public User checkPassword(@RequestParam(name="data") String password) {
//
//    }
}
