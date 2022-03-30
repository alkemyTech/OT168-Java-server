package com.alkemy.ong.web.pagination;

import com.alkemy.ong.data.pagination.PageModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageDTOMapper<T,S>{
    private final ModelMapper modelMapper;

    public PageDTOMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    public PageDTO<T> toPageDTO(PageModel<S> pageModel, Class<T> DTOClass){
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

    public T toDTO (S model, Class<T> DTOClass){
        return modelMapper.map(model, DTOClass);
    }
}
