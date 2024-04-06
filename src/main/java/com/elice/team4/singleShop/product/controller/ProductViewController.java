package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductViewController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @GetMapping("/seller/new")
    public String createForm(Model model) {
        List<Category> categories = categoryService.findCategories(); // 모든 카테고리를 가져오는 메서드 호출
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "products/add/product-add";
    }

    @GetMapping("/products")
    public String list(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "products/list/product-list";
    }

    @GetMapping("/seller/{productId}")
    public String updateProductForm(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> productOptional = productService.findProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDto productDto = productMapper.productToProductDto(product);

            List<Category> categories = categoryService.findCategories(); // 모든 카테고리를 가져오는 메서드 호출
            model.addAttribute("categories", categories);
            model.addAttribute("productDto", productDto);

            return "products/edit/product-edit";
        } else {
            return "redirect:/products";
        }
    }
}
