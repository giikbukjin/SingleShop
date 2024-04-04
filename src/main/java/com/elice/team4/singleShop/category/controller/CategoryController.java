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
@AllArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        return "home/home";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        Category foundCategory = categoryService.findCategory(id);

        model.addAttribute("category", foundCategory);
        //TODO: category 를 쏘아 줄 html 페이지 경로 지정
        return "category/category";
    }

    @GetMapping("/add")
    public String createCategoryGet(Model model) {
        //TODO: category 생성 html 페이지 경로 지정
        return "category-add/category-add";
    }

    @PostMapping("/add")
    public String createCategoryPost(@ModelAttribute CategoryDto categoryDto) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);

        Category savedCategory = categoryService.createCategory(category);

        //TODO: redirect 경로 지정 - 카테고리 관리 페이지 URI
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryGet(@PathVariable Long id, Model model) {
        Category category = categoryService.findCategory(id);
        model.addAttribute("category", category);

        //TODO: category 수정 html 페이지 경로 지정
        return "category-edit/category-edit";
    }

    @PostMapping("/{id}/edit")
    public String editCategoryPost(@PathVariable Long id,
                                   @ModelAttribute CategoryDto categoryDto,
                                   RedirectAttributes redirectAttributes) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        Category updatedCategory = categoryService.updateCategory(category, id);

        redirectAttributes.addAttribute("categoryId", updatedCategory.getId());
        redirectAttributes.addFlashAttribute("message", "카테고리가 수정되었습니다.");

        //TODO: redirect 경로 지정 - 해당 카테고리 상세 페이지
        return "redirect:/admin/category/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");

        //TODO: redirect 경로 지정 - 카테고리 관리 페이지
        return "redirect:/admin";
    }
}