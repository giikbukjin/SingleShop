package com.elice.team4.singleShop.category.controller;

import com.elice.team4.singleShop.category.dto.CategoryDto;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.mapper.CategoryMapper;
import com.elice.team4.singleShop.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

        return "categories/admin-categories";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        Category foundCategory = categoryService.findCategory(id);

        model.addAttribute("category", foundCategory);

        return "category/category";
    }

    @GetMapping("/add")
    public String createCategoryGet(Model model) {

        return "category-add/category-add";
    }

    @PostMapping("/add")
    public String createCategoryPost(@ModelAttribute @Validated CategoryDto categoryDto,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "category-error/category-add-error";
        }

        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);

        Category savedCategory = categoryService.createCategory(category);

        return "redirect:/admin/category";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryGet(@PathVariable Long id, Model model) {
        Category category = categoryService.findCategory(id);
        model.addAttribute("category", category);

        return "category-edit/category-edit";
    }

    @PostMapping("/{id}/edit")
    public String editCategoryPost(@PathVariable Long id,
                                   @ModelAttribute @Validated CategoryDto categoryDto,
                                   BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "category-error/category-edit-error";
        }

        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        Category updatedCategory = categoryService.updateCategory(category, id);

        return "redirect:/admin/category/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);

        // Modal 창을 사용할 경우
        redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");

        return "redirect:/admin/category";
    }
}