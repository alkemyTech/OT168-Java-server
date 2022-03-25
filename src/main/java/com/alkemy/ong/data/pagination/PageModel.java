package com.alkemy.ong.data.pagination;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageModel<S> {
    private List<S> body;
    private String nextPage;
    private String previousPage;
}
