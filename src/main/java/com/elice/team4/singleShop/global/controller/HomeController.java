package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    // TODO: URI, 메서드명 협의 후 변동 있으면 수정
    @GetMapping("/home")
    public String getMainContents(Model model) {
        // TODO: List<Category> 얻어서 model 에 담기
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);

        // TODO: List<Product> 얻어서 model 에 담기


        // TODO: 로그인 관련 정보 얻어서 model 에 담기 (?)

        return "home/home";
    }
}
