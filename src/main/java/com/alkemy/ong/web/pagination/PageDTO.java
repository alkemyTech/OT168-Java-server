package com.alkemy.ong.web.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageDTO<T> {

    private List<T> body;

    @Schema(example = "This is the first page", required = true)
    private String previuosPage;

    @Schema(example = "This is the last page", required = true)
    private String nextPage;
}
