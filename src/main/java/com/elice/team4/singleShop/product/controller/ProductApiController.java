package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @PostMapping("/seller/new")
    public String create(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        productService.saveProduct(productDto);
        redirectAttributes.addFlashAttribute("success", "Product registered successfully!");
        return "redirect:/products";
    }

    @PostMapping("/seller/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, @ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        productService.updateProduct(productId, productDto);
        redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        return "redirect:/products";
    }

    @PostMapping("/seller/{productId}/delete")
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully");
        return "redirect:/products";
    }
}
