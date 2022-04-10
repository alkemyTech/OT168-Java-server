package com.alkemy.ong.web.pagination;

import com.alkemy.ong.data.pagination.PageModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class PageDTOMapper<T,S>{
    private final ModelMapper modelMapper;

    public PageDTOMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    public PageDTO<T> toPageDTO(PageModel<S> pageModel, Class<T> DTOClass){
        PageDTO<T> pageDTO = new PageDTO<>();

        List<T> bodyDTO = pageModel.getBody()
                .stream()
                .map(m->toDTO(m,DTOClass))
                .collect(toList());
        pageDTO.setBody(bodyDTO);

        pageDTO.setPreviuosPage(pageModel.getPreviousPage());
        pageDTO.setNextPage(pageModel.getNextPage());

        return pageDTO;
    }

    public T toDTO (S model, Class<T> DTOClass){
        return modelMapper.map(model, DTOClass);
    }
}
