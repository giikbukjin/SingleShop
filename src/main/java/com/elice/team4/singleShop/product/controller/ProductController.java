package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/view/seller/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ProductForm());
        return "products/createProductForm";
    }

    @PostMapping("/api/seller/new")
    public String create(ProductForm form) {
        Product product = new Product();

        product.setName(form.getName());
        product.setCategory(form.getCategory());
        product.setSummary(form.getSummary());
        product.setDescription(form.getDescription());
        product.setImage(form.getImage());
        product.setStock(form.getStock());
        product.setPrice(form.getPrice());

        productService.saveProduct(product);
        return "redirect:/";
    }
}
