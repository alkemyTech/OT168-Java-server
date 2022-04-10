package com.alkemy.ong.data.pagination;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class PageModelMapper<T, S> {

    private final ModelMapper modelMapper;

    public PageModelMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    public PageModel<T> toPageModel(PageModel<S> pageEntity,Class<T> modelClass){
        PageModel<T> pageModel = new PageModel<T>();

        List<T> bodyModel = pageEntity.getBody()
                .stream()
                .map(e->toModel(e,modelClass))
                .collect(toList());

        pageModel.setNextPage(pageEntity.getNextPage());
        pageModel.setPreviousPage(pageEntity.getPreviousPage());
        pageModel.setBody(bodyModel);

        return pageModel;
    }

    public T toModel (S entity,Class<T> modelClass){
        return modelMapper.map(entity,modelClass);
    }

}