package com.elice.team4.singleShop.product.mapper;

import com.elice.team4.singleShop.product.controller.ProductForm;
import com.elice.team4.singleShop.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductForm form);
}