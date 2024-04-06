package com.elice.team4.singleShop.product.mapper;

import com.elice.team4.singleShop.category.mapper.CategoryMapper;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto productToProductDto(Product product);

    @Mapping(target = "category", ignore = true) // DTO에서 엔티티로 변환 시 카테고리는 수동으로 처리
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(target = "id", ignore = true) // ID 필드는 매핑에서 제외
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}

