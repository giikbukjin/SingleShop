package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductViewController {
    private final ProductService productService;

    @GetMapping("/seller/new")
    public String createForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/createProductForm";
    }
}
