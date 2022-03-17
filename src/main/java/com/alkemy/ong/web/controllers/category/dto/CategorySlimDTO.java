package com.alkemy.ong.web.controllers.category.dto;

import com.alkemy.ong.domain.usecases.category.Category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class CategorySlimDTO {
	private String name;
	
	public CategorySlimDTO() {
		super();
	}

	public CategorySlimDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private static CategorySlimDTO from(Category category) {
		return new CategorySlimDTO(category.getName());
	}
	
	public Category fromThis() {
		return new Category(name);
	}		

}
