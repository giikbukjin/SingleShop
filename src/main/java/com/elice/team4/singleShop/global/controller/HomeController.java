package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.service.ProductService;
import com.elice.team4.singleShop.user.oauth.KakaoApi;
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
    private final KakaoApi kakaoApi;

    @GetMapping("/")
    public String initialAccess() {
        return "redirect:/home";
    }

    @GetMapping("/page-not-found")
    public String errorPage(){return "page-not-found/page-not-found";}

    @GetMapping("/home")
    public String getMainContents(Model model) {
        // TODO: List<Category> 얻어서 model 에 담기
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);

        // TODO: List<Product> 얻어서 model 에 담기
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);


        // TODO: 로그인 관련 정보 얻어서 model 에 담기 (?)

        //카카오 로그아웃 관련 model객체
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectLogoutUri", kakaoApi.getKakaoLogoutRedirectUri());

        return "home/home";
    }
}
