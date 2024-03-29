package com.elice.team4.singleShop.category.mapper;

import com.elice.team4.singleShop.category.dto.CategoryDto;
import com.elice.team4.singleShop.category.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T19:16:53+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category CategoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( categoryDto.getCategoryName() );
        category.setCategoryContent( categoryDto.getCategoryContent() );

        return category;
    }
}
