package com.alkemy.ong.data.pagination;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageModelMapper<T, S> {

    private final ModelMapper modelMapper;

    public PageModelMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    public PageModel<T> toPageModel(PageModel<S> pageEntity,Class<T> modelClass){
        PageModel<T> pageModel = new PageModel<T>();
        List<T> bodyModel = new ArrayList<>();

        pageModel.setNextPage(pageEntity.getNextPage());
        pageModel.setPreviousPage(pageEntity.getPreviousPage());

        for(S entity : pageEntity.getBody()){
            T model = toModel(entity,modelClass);
            bodyModel.add(model);
        }

        pageModel.setBody(bodyModel);
        return pageModel;
    }

    public T toModel (S entity,Class<T> modelClass){
        return modelMapper.map(entity,modelClass);
    }

}