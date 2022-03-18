package com.alkemy.ong.data.gateways;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryGateway;

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
				.collect(toList());
	}

}
