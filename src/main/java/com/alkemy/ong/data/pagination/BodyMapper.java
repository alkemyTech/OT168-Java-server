package com.alkemy.ong.data.pagination;

import com.alkemy.ong.web.utils.PageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BodyMapper<T, S> {

    private final ModelMapper modelMapper;

    public BodyMapper(ModelMapper modelMapper){
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
        pageModel.getBody().get(0);
        return pageModel;
    }

    public PageDTO<T> toPageDTO(PageModel<S>pageModel, Class<T> DTOClass){
        PageDTO<T> pageDTO = new PageDTO<>();
        List<T> bodyDTO = new ArrayList<>();

        pageDTO.setPreviuosPage(pageModel.getPreviousPage());
        pageDTO.setNextPage(pageModel.getNextPage());

        for(S model : pageModel.getBody()){
            T DTO = toDTO(model,DTOClass);
            bodyDTO.add(DTO);
        }

        pageDTO.setBody(bodyDTO);

        return pageDTO;
    }

    public T toModel (S entity,Class<T> modelClass){
        T model = modelMapper.map(entity,modelClass);
        return model;
    }

    public T toDTO (S model, Class<T> DTOClass){
        T DTO = modelMapper.map(model, DTOClass);
        return DTO;
    }
}