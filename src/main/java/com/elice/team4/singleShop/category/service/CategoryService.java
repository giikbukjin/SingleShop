package com.elice.team4.singleShop.category.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.global.exception.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategory(Long id) {
        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());

        return foundCategory;
    }

    public Category findCategoryByName(String categoryName) {
        Category foundCategory = categoryRepository.findByCategoryName(categoryName);
        return foundCategory;
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category, Long categoryId) {
        category.setId(categoryId);
        Category foundCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new CategoryNotFoundException());

        foundCategory.update(category);

        return categoryRepository.save(foundCategory);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());

        categoryRepository.delete(foundCategory);
    }
}
