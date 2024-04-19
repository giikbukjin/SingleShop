package com.elice.team4.singleShop.product.controller;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import com.elice.team4.singleShop.product.mapper.ProductMapper;
import com.elice.team4.singleShop.product.service.ProductService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductViewController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final UserDetailsServiceImpl userService;

    @GetMapping("/seller/new")
    public String createForm(Model model) {
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "products/add/product-add";
    }

    @GetMapping("/seller/products")
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

            // 카테고리 ID 설정
            if (product.getCategory() != null) {
                productDto.setCategoryId(product.getCategory().getId());
            }

            List<Category> categories = categoryService.findCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("productDto", productDto);
            model.addAttribute("product", product);

            return "products/edit/product-edit";
        } else {
            return "redirect:/seller/products";
        }
    }

    @GetMapping("/products/{productId}")
    public String getProductDetail(@PathVariable("productId") Long productId, Model model, Principal principal) {
        Optional<Product> productOptional = productService.findProductById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);

            // Principal 객체가 존재하는 경우에만 사용자 정보를 추가합니다.
            if (principal != null) {
                String currentUserName = principal.getName();
                User currentUser = (User) userService.loadUserByUsername(currentUserName);
                model.addAttribute("currentUser", currentUser);
            }
        }
        return "products/detail/product-detail";
    }

    @GetMapping("/seller/products/{productId}")
    public String showSellerProductDetail(@PathVariable Long productId, Model model) {
        Product product = productService.findProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        model.addAttribute("product", product);
        return "products/detail/seller-product-detail"; // 상품 상세 페이지의 HTML 파일 이름
    }
}