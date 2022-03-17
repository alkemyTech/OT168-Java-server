package com.alkemy.ong.data.gateways;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.usecases.category.Category;
import com.alkemy.ong.domain.usecases.category.CategoryGateway;

@Component
public class DefaultCategoryGateway implements CategoryGateway{

	private final CategoryRepository categoriRepository;
	
	public DefaultCategoryGateway(CategoryRepository categoriRepository) {
		this.categoriRepository = categoriRepository;
	}
	
	@Override
	public List<Category> findAll() {
		return categoriRepository.findAll()
				.stream()
				.map(CategoryEntity::fromThis)
				.collect(Collectors.toList());
	}

}
