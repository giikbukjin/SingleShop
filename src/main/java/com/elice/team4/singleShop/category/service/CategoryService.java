package com.elice.team4.singleShop.category.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.global.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());

        return foundCategory;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category, Long categoryId) {
        category.setId(categoryId);
        Category foundCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new CategoryNotFoundException());

        foundCategory.update(category);

        return categoryRepository.save(foundCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());

        categoryRepository.delete(foundCategory);
    }
}
