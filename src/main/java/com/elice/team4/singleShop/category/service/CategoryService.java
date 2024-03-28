package com.elice.team4.singleShop.category.service;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId).orElse(null);
        // TODO: 카테고리 미조회 예외처리 Exception 클래스 만들기
//        if(foundCategory == null) {
//            throw new CategoryNotFoundException();
//        }
        return foundCategory;
    }
}
