package com.alkemy.ong.domain.pagination;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenericModelPage<T,S> {
    private Page<T> modelPage;
    private List<S> listModel;
    private String nextPage;
    private String previousPage;
}
