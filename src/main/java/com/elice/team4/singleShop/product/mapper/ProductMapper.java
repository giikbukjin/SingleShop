package com.elice.team4.singleShop.product.mapper;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.product.controller.ProductForm;
import com.elice.team4.singleShop.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // ProductForm의 category 필드에서 Product의 category 필드로 매핑을 정의합니다.
    // categoryName 필드가 있다고 가정하고 매핑합니다.
    @Mapping(target = "category", source = "category", qualifiedByName = "stringToCategory")
    Product toProduct(ProductForm form);

    @Named("stringToCategory")
    default Category stringToCategory(String categoryName) {
        // Category 객체 생성 로직
        return new Category(categoryName); // 실제 구현에 맞게 수정 필요
    }
}