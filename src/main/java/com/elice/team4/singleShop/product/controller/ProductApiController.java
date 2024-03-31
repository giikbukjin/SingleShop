package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @PostMapping("/api/seller/new")
    public String create(ProductForm form) {
        Product product = ProductMapper.INSTANCE.toProduct(form);
        productService.saveProduct(product);
        return "redirect:/";
    }
}
