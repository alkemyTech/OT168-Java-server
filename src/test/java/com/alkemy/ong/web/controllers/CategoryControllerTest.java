package com.alkemy.ong.web.controllers;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CategoryRepository categoryRepository;

	ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void getCategoryByIdSuccessTest() throws Exception {
		CategoryEntity categoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

		mockMvc.perform(get("/categories/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"));

		verify(categoryRepository).findById(1L);
	}

	@Test
	void getCategoryByIdNotFoundTest() throws Exception {

		doThrow(ResourceNotFoundException.class).when(categoryRepository).findById(999L);

		mockMvc.perform(get("/categories/999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(categoryRepository).findById(999L);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void saveCategorySuccessTest() throws Exception {
		CategoryEntity categoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

		mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryEntity))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"));

		verify(categoryRepository).save(categoryEntity);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void saveCategoryBadRequestTest() throws Exception {

		CategoryEntity categoryEntity = toEntity(null, null, null, null);

		mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryEntity))).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void updateCategorySuccessTest() throws Exception {
		CategoryEntity updateCategoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(updateCategoryEntity.getId())).thenReturn(Optional.of(updateCategoryEntity));
		when(categoryRepository.save(updateCategoryEntity)).thenReturn(updateCategoryEntity);

		mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateCategoryEntity))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"));

		verify(categoryRepository).save(updateCategoryEntity);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void updateCategoryFailTest() throws Exception {
		CategoryEntity updateCategoryEntity = toEntity(1L, null, null, null);

		when(categoryRepository.findById(updateCategoryEntity.getId())).thenReturn(Optional.of(updateCategoryEntity));
		when(categoryRepository.save(updateCategoryEntity)).thenReturn(updateCategoryEntity);

		mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateCategoryEntity))).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deleteCategorySuccessTest() throws Exception {
		CategoryEntity categoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

		mockMvc.perform(delete("/categories/1")).andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deleteCategoryByIdNotFoundTest() throws Exception {
		doThrow(ResourceNotFoundException.class).when(categoryRepository).deleteById(999L);

		mockMvc.perform(delete("/categories/999")).andExpect(status().isNotFound());
	}

	@Test
	void listCategoriesPageSuccessTest() throws Exception {

		List<CategoryEntity> categoryList = Arrays.asList(toEntity(1L, "RRHH", "category of RRHH", "image.png"),
				toEntity(2L, "Test", "category of test", "image.png"),
				toEntity(3L, "Marketing", "category of marketing", "image.png"));

		when(categoryRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(categoryList));

		mockMvc.perform(get("/categories").param("page", "0").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryList))).andExpect(status().isOk())
				.andExpect(jsonPath("body").isArray()).andExpect(content().contentType("application/json"));
	}

	private CategoryEntity toEntity(Long id, String name, String description, String image) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(id);
		categoryEntity.setName(name);
		categoryEntity.setDescription(description);
		categoryEntity.setImage(image);
		categoryEntity.setDeleted(false);
		return categoryEntity;
	}

}