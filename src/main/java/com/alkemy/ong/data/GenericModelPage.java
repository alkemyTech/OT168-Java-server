package com.alkemy.ong.data;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenericModelPage<T> {
    private Page<T> entityPage;
    //private List<S> listModel;
    private String nextPage;
    private String previousPage;
}
