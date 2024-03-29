package com.elice.team4.singleShop.category.controller;

import com.elice.team4.singleShop.category.dto.CategoryDto;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.mapper.CategoryMapper;
import com.elice.team4.singleShop.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        // TODO: categories 를 쏘아 줄 html 페이지 경로 지정. home 화면.
        return "";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable Long categoryId, Model model) {
        Category foundCategory = categoryService.findCategory(categoryId);

        model.addAttribute("category", foundCategory);
        //TODO: category 를 쏘아 줄 html 페이지 경로 지정
        return "";
    }

    @GetMapping("/add")
    public String createCategoryGet(Model model) {
        //TODO: category 생성 html 페이지 경로 지정
        return "";
    }

    @PostMapping("/add")
    public String createCategoryPost(@ModelAttribute CategoryDto categoryDto) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);

        Category savedCategory = categoryService.createCategory(category);

        //TODO: redirect 경로 지정 - 카테고리 관리 페이지
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryGet(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findCategory(categoryId);
        model.addAttribute("category", category);

        //TODO: category 수정 html 페이지 경로 지정
        return "";
    }

    @PostMapping("/{id}/edit")
    public String editCategoryPost(@PathVariable Long categoryId,
                                   @ModelAttribute CategoryDto categoryDto,
                                   RedirectAttributes redirectAttributes) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        Category updatedCategory = categoryService.updateCategory(category, categoryId);

        redirectAttributes.addAttribute("categoryId", updatedCategory.getId());
        redirectAttributes.addFlashAttribute("message", "카테고리가 수정되었습니다.");

        //TODO: redirect 경로 지정 - 해당 카테고리 상세 페이지
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(categoryId);
        redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");

        //TODO: redirect 경로 지정 - 카테고리 관리 페이지
        return "redirect:";
    }
}
