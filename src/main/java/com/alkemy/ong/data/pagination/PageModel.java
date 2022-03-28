package com.alkemy.ong.data.pagination;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageModel<T> {
    private List<T> body;
    private String nextPage;
    private String previousPage;
}
