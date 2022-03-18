package com.alkemy.ong.domain.category;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
public class Category {

	private Long id;
	private String name;
	private String description;
	private String image;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean deleted;

}