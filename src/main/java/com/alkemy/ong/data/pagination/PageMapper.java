package com.alkemy.ong.data.pagination;

import com.alkemy.ong.web.utils.PageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageMapper<T, S> {

    private final ModelMapper modelMapper;

    public PageMapper(ModelMapper modelMapper){
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
        return modelMapper.map(entity,modelClass);
    }

    public T toDTO (S model, Class<T> DTOClass){
        return modelMapper.map(model, DTOClass);
    }
}