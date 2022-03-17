package com.alkemy.ong.web.controllers.category;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.alkemy.ong.domain.usecases.category.CategoryService;
import com.alkemy.ong.web.controllers.category.dto.CategorySlimDTO;
import com.alkemy.ong.web.mapper.CategoryMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Tag(name = "Categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;
	
	public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
		this.categoryService = categoryService;
		this.categoryMapper = categoryMapper;
	}
		
	@GetMapping
	public List<CategorySlimDTO> getAllCategories(){
		return categoryMapper.categoriesToCategorieSlimDtos(categoryService.findAll());				
	}
	
}
