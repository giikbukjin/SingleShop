package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.global.service.AdminPageService;
import com.elice.team4.singleShop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPageController {

    private final AdminPageService adminPageService;

    @GetMapping
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String showAllUsersList () {
        return "admin-users/admin-users";
    }

    @GetMapping("/logout")
    public String adminLogout() {
        return "redirect:/auth/logout";
    }
}