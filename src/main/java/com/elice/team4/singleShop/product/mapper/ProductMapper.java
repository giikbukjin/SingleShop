package com.elice.team4.singleShop.product.mapper;

import com.elice.team4.singleShop.category.mapper.CategoryMapper;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {CategoryMapper.class}
) // CategoryMapper를 사용하여 카테고리 매핑 처리
public interface ProductMapper {
    @Mapping(source = "id", target = "category.id") // categoryId 필드를 category 엔티티의 ID로 매핑
    Product productDtoToProduct(ProductDto productDto);
}
