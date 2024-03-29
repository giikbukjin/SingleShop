package com.elice.team4.singleShop.category.mapper;

import org.mapstruct.Mapper;

import com.elice.team4.singleShop.category.dto.CategoryDto;
import com.elice.team4.singleShop.category.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category CategoryDtoToCategory(CategoryDto categoryDto);
}
