package com.alkemy.ong.web.controllers;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.alkemy.ong.data.utils.PaginationUtils.DEFAULT_PAGE_SIZE;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.pagination.PageModel;
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

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void getCategoryByIdSuccessTest() throws Exception {
		CategoryEntity categoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

		mockMvc.perform(get("/categories/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"))
				.andExpect(jsonPath("$.image").value("image.png"));
	}

	@Test
	void getCategoryByIdNotFoundTest() throws Exception {

		doThrow(ResourceNotFoundException.class).when(categoryRepository).findById(999L);

		mockMvc.perform(get("/categories/999").contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void saveCategorySuccessTest() throws Exception {
		CategoryEntity categoryEntityWithoutId = toEntity(null, "Test", "category of test", "image.png");
		CategoryEntity categoryEntityWithId = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.save(categoryEntityWithId)).thenReturn(categoryEntityWithId);

		mockMvc.perform(post("/categories").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryEntityWithId)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"))
				.andExpect(jsonPath("$.image").value("image.png"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void saveCategoryBadRequestTest() throws Exception {

		CategoryEntity categoryEntity = toEntity(null, null, "category of RRHH", "image.png");

		mockMvc.perform(post("/categories").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryEntity))).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void updateCategorySuccessTest() throws Exception {
		CategoryEntity updateCategoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(updateCategoryEntity.getId())).thenReturn(Optional.of(updateCategoryEntity));
		when(categoryRepository.save(updateCategoryEntity)).thenReturn(updateCategoryEntity);

		mockMvc.perform(put("/categories/1").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateCategoryEntity))).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("RRHH"))
				.andExpect(jsonPath("$.description").value("category of RRHH"))
				.andExpect(jsonPath("$.image").value("image.png"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void updateCategoryFailTest() throws Exception {
		CategoryEntity updateCategoryEntity = toEntity(1L, null, "category of RRHH", "image.png");

		when(categoryRepository.findById(updateCategoryEntity.getId())).thenReturn(Optional.of(updateCategoryEntity));
		when(categoryRepository.save(updateCategoryEntity)).thenReturn(updateCategoryEntity);

		mockMvc.perform(put("/categories/1").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateCategoryEntity))).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deleteCategorySuccessTest() throws Exception {
		CategoryEntity categoryEntity = toEntity(1L, "RRHH", "category of RRHH", "image.png");

		when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));
		doNothing().when(categoryRepository).deleteById(categoryEntity.getId());

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
		PageModel<CategoryEntity> pageModelCategories = buildPageModel();
		
		when(categoryRepository.findAll(PageRequest.of(0,DEFAULT_PAGE_SIZE))).thenReturn(new PageImpl<>(pageModelCategories.getBody()));
		
		mockMvc.perform(get("/categories").param("page", "0").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pageModelCategories)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("body").isArray())
				.andExpect(jsonPath("$.body",hasSize(2)))
				.andExpect(jsonPath("$.body.[0].name",is("RRHH")))
				.andExpect(jsonPath("$.body.[1].name",is("Test")))
				.andExpect(jsonPath("$.nextPage",is("This is the last page")))
                .andExpect(jsonPath("$.previuosPage",is("This is the first page")))
				.andExpect(content().contentType("application/json"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void findAllBadRequest() throws Exception {
		PageModel<CategoryEntity> pageModelCategories = buildPageModel();
		
		mockMvc.perform(get("/members?page=")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pageModelCategories)))
        .andExpect(status().isBadRequest());
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
	
	private PageModel buildPageModel(){
        return PageModel.builder()
                .body(Arrays.asList(toEntity(1L, "RRHH", "category of RRHH", "image.png"), toEntity(2L, "Test", "category of test", "image.png")))
                .nextPage("This is the last page")
                .previousPage("This is the first page")
                .build();
    }

}