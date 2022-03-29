package com.alkemy.ong.data.gateways;

import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.pagination.PageMapper;
import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.data.utils.PaginationUtils;
import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

	private final CategoryRepository categoryRepository;
	private final PageMapper<Category, CategoryEntity> pageMapper;

	public DefaultCategoryGateway(CategoryRepository categoryRepository, PageMapper<Category, CategoryEntity> bodyMapper) {
		this.categoryRepository = categoryRepository;
		this.pageMapper = bodyMapper;
	}

	@Override
	public PageModel<Category> findAll(int pageNumber) {
		return pageMapper.toPageModel(PaginationUtils.setPagesNumbers(categoryRepository
                .findAll(PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE)),"/members?page="),Category.class);
	}

	@Override
	public Category findById(Long id) {
		CategoryEntity categoryEntity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id, "category"));
		return toModel(categoryEntity);
	}
	
	@Override
	public Category save(Category category) {
		return toModel(categoryRepository.save(toEntity(category)));
	}
	
	@Override
	public Category update(Long id, Category category) {
		CategoryEntity categoryEntity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id, "category"));
		categoryEntity.setName(category.getName());
		categoryEntity.setDescription(category.getDescription());
		categoryEntity.setName(category.getName());
		return toModel(categoryRepository.save(categoryEntity));
	}

	@Override
	public void delete(Long id) {
		CategoryEntity categoryEntity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id, "category"));
		categoryEntity.setDeleted(true);
		categoryRepository.save(categoryEntity);
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
