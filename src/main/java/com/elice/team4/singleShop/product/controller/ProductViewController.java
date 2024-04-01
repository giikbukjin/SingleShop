package com.elice.team4.singleShop.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    @GetMapping("/view/seller/new")
    public String createForm(Model model) {
        return "products/createProductForm";
    }
}
