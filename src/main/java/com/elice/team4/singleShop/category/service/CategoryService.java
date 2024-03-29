package com.elice.team4.singleShop.category.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.global.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Category foundCategory = categoryRepository.findById(categoryId).orElse(null);
        if(foundCategory == null) {
            throw new CategoryNotFoundException();
        }
        return foundCategory;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
