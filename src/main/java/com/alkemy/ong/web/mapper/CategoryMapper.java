package com.alkemy.ong.web.mapper;

import java.util.List;

import org.mapstruct.*;

import com.alkemy.ong.domain.usecases.category.Category;
import com.alkemy.ong.web.controllers.category.dto.CategoryDTO;
import com.alkemy.ong.web.controllers.category.dto.CategorySlimDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	CategoryDTO categoryToCategoryDto(Category category);
	
	Category categoryDtoToCategory(CategoryDTO categoryDTO);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
	Category updateCategoryFromDto(CategoryDTO categoryDto, @MappingTarget Category category);
	
    List<CategoryDTO> categoriesToCategoriesDtos(List<Category> categories);
    
    List<CategorySlimDTO> categoriesToCategorieSlimDtos(List<Category> categories);

}
