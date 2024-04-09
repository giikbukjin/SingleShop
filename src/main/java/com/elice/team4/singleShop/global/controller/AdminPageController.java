package com.elice.team4.singleShop.global.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String showAllUsersList(Model model){
        return "admin-users/admin-users";
    }}
