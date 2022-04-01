package com.alkemy.ong.web.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("previuosPage")
    @Schema(example = "This is the first page", required = true)
    private String previuosPage;

    @JsonProperty("nextPage")
    @Schema(example = "This is the last page", required = true)
    private String nextPage;
}
