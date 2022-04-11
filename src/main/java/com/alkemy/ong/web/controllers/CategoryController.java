package com.alkemy.ong.web.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.alkemy.ong.web.pagination.PageDTO;
import com.alkemy.ong.web.pagination.PageDTOMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.domain.category.Category;
import com.alkemy.ong.domain.category.CategoryService;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;

import lombok.*;

@Tag(name = "Categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final PageDTOMapper<CategorySlimDTO, Category> pageDTOMapper;

	public CategoryController(CategoryService categoryService, PageDTOMapper pageDTOMapper) {
		this.categoryService = categoryService;
		this.pageDTOMapper = pageDTOMapper;
	}

	 @Operation(summary = "Get a paginated list of categories")
	    @ApiResponses(
	    		value = {
	    				@ApiResponse(
	    						responseCode = "200", 
	    						description = "Retrieve a paginated list of categories",
	    						content = {
	    								@Content(
	    										mediaType = "application/json",
	    										schema = @Schema(
	    												implementation = CategorySlimDTO.class)) }),
	    				@ApiResponse(
	                            responseCode = "400",
	                            description = "BAD REQUEST",
	                            content = {
	                                    @Content(
	                                            schema = @Schema(implementation = String.class),
	                                            examples = @ExampleObject(
	                                                    name = "Message of error",
	                                                    summary = "400 from the server directly.",
	                                                    value = "The page does not exist"
	                                            )
	                                    )
	                            }
	                    )
	    })
	@GetMapping
	public ResponseEntity<PageDTO<CategorySlimDTO>> getAllCategories(@RequestParam("page") int numberPage) {
		WebUtils.validatePageNumber(numberPage);
        return ResponseEntity.ok().body(pageDTOMapper.toPageDTO(categoryService.findAll(numberPage),CategorySlimDTO.class));
	}

	@Operation(summary = "Get a category by id")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200", 
							description = "Found category", 
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = CategoryDTO.class)) }),
					@ApiResponse(
							responseCode = "404", 
							description = "Category not found",
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = ResourceNotFoundException.class),
											examples = @ExampleObject(
                                                    name = "Message of error",
                                                    summary = "404 from the server directly.",
                                                    value = "The ID doesn't exist."
                                            )) })
	})
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable("id") Long id) {
		Category category = categoryService.findById(id);
		CategoryDTO categoryDTO = toDTO(category);
		return ResponseEntity.ok(categoryDTO);
	}

	@Operation(summary = "Add a new category to the database")
    @ApiResponses(
    		value = {
            @ApiResponse(
            		responseCode = "201",
            		description = "Create category",
            		content = @Content),
            @ApiResponse(
            		responseCode = "400", 
            		description = "Invalid field",
            		content = { 
            				@Content(
            						mediaType = "application/json",
            						schema = @Schema(implementation = String.class),
            						examples = @ExampleObject(
                                            name = "Message of error",
                                            summary = "400 from the server directly.",
                                            value = "\"The name field is required.\" or \"The name field must not have numbers.\""))
            		})
    })
	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(categoryService.save(toModel(categoryDTO))));
	}

	@Operation(summary = "Update category")
    @ApiResponses(
    		value = {
            @ApiResponse(
            		responseCode = "200",
            		description = "Update category",
            		content = @Content),
            @ApiResponse(
            		responseCode = "400", 
            		description = "Invalid field",
            		content = { 
            				@Content(
            						mediaType = "application/json",
            						schema = @Schema(implementation = String.class),
            						examples = @ExampleObject(
                                            name = "Message of error",
                                            summary = "400 from the server directly.",
                                            value = "\"The name field is required.\" or \"The name field must not have numbers.\""))
            		})
    })
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(toDTO(categoryService.update(id, toModel(categoryDTO))));
	}

	@Operation(summary = "Delete a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the category",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResourceNotFoundException.class),
                            examples = @ExampleObject(
                                    name = "Message of error",
                                    summary = "404 from the server directly.",
                                    value = "The ID doesn't exist.")
                            ) }),

    })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CategorySlimDTO toSlimDTO(Category category) {
		CategorySlimDTO newCategorySlimDTO = CategorySlimDTO.builder().name(category.getName()).build();
		return newCategorySlimDTO;
	}

	private CategoryDTO toDTO(Category category) {
		return CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.image(category.getImage())
				.createdAt(category.getCreatedAt())
				.updatedAt(category.getUpdatedAt())
				.build();
	}

	private Category toModel(CategoryDTO categoryDTO) {
		return Category.builder()
				.id(categoryDTO.getId())
				.name(categoryDTO.getName())
				.description(categoryDTO.getDescription())
				.image(categoryDTO.getImage())
				.createdAt(categoryDTO.getCreatedAt())
				.updatedAt(categoryDTO.getUpdatedAt())
				.build();
	}
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class CategorySlimDTO {
		@Schema(example = "RRHH", required = true)
		private String name;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class CategoryDTO {
		@Schema(example = "1", required = true)
		private Long id;
		@Schema(example = "RRHH", required = true)
		@NotEmpty(message = "The name field is required.")
		@Pattern(regexp = "[a-zA-Z]{0,255}", message = "The name field must not have numbers.")
		private String name;
		@Schema(example = "some description of the category", required = true)
		private String description;
		@Schema(example = "photo.jpg", required = true)
		private String image;
		@Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-04-02 18:58:56")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime createdAt;
		@Schema(pattern = "yyyy-MM-dd HH:mm:ss", example = "2022-04-02 18:58:56")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime updatedAt;
	}
}