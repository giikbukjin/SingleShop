package com.elice.team4.singleShop.category.controller;

import com.elice.team4.singleShop.category.dto.CategoryDto;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.mapper.CategoryMapper;
import com.elice.team4.singleShop.category.service.CategoryService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ProductService productService;
    private final String uploadPath;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper,
                              ProductService productService, @Value("${uploadPath}") String uploadPath) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.productService = productService;
        this.uploadPath = uploadPath;
    }

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);

        return "categories/admin-categories";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable Long id,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) String keyword,
                              Model model) {
        Category foundCategory = categoryService.findCategory(id);
        PageRequest pageRequest = PageRequest.of(page, size);

        // TODO : Page<Product> 타입 객체 얻어오기, ProductService 메서드
        Page<Product> productPage = productService.findProductsByCategoryAndKeyword(foundCategory, keyword, pageRequest);

        model.addAttribute("category", foundCategory);
        model.addAttribute("keyword", keyword);
        model.addAttribute("productPage", productPage);

        // TODO: category.html 타임리프 작업 미완성, 'th:' 검색하여 수정할 것
        return "category/category";
    }

    @GetMapping("/add")
    public String createCategoryGet(Model model) {

        return "category-add/category-add";
    }

    @PostMapping("/add")
    public String createCategoryPost(@ModelAttribute @Validated CategoryDto categoryDto,
                                     RedirectAttributes redirectAttributes,
                                     BindingResult bindingResult,
                                     @RequestParam("files") MultipartFile[] files) throws IOException {

        UUID uuid = UUID.randomUUID();

        if (files != null) {
            for(MultipartFile file : files) {
                if (file != null) {
                    String fileName = uuid + "_" + file.getOriginalFilename();
                    File upFile = new File(uploadPath, fileName);
                    file.transferTo(upFile);
                    categoryDto.setImageFileName(fileName);
                }
            }
        }

        if(bindingResult.hasErrors()) {
            return "category-error/category-add-error";
        }

        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);

        Category savedCategory = categoryService.createCategory(category);

        redirectAttributes.addFlashAttribute("uploadPath", uploadPath);

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

        // TODO: category.html 완성 후 admin/category/{id} 로 변경
        return "redirect:/admin/category";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);

        // Modal 창을 사용할 경우
        redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");

        return "redirect:/admin/category";
    }
}