package com.alkemy.ong.data.gateways;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

	private final CategoryRepository categoryRepository;

	public DefaultCategoryGateway(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories;
		categories = categoryRepository.findAll().stream().map(cat -> toModel(cat)).collect(toList());
		return categories;
	}

	@Override
	public Category findById(Long id) {
		CategoryEntity categoryEntity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The id %d doesn't exist.", id));
		return toModel(categoryEntity);
	}
	
	@Override
	public Category save(Category category) {
		return toModel(categoryRepository.save(toEntity(category)));
	}

	private Category toModel(CategoryEntity categoryEntity) {
		Category newCategory = Category.builder()
				.id(categoryEntity.getId())
				.name(categoryEntity.getName())
				.description(categoryEntity.getDescription())
				.image(categoryEntity.getImage())
				.createdAt(categoryEntity.getCreatedAt())
				.updatedAt(categoryEntity.getUpdatedAt())
				.deleted(categoryEntity.getDeleted())
				.build();
		return newCategory;
	}
	
	private CategoryEntity toEntity(Category category) {
		CategoryEntity newCategoryEntity = CategoryEntity.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.image(category.getImage())
				.createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt())
				.build();
		return newCategoryEntity;
	}

}
