package com.elice.team4.singleShop.global.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/admin";
    }
}
