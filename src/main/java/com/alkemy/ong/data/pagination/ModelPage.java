package com.alkemy.ong.data.pagination;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ModelPage<S> {
    private List<S> modelList;
    private String nextPage;
    private String previuosPage;
}
