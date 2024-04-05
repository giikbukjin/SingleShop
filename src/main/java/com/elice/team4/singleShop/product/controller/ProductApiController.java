package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @PostMapping("/products")
    public String create(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        productService.saveProduct(productDto); // Save the product
        redirectAttributes.addFlashAttribute("success", "Product registered successfully!"); // Optional: Add success message
        return "redirect:/seller/new"; // Redirect back to the registration page
    }

}
