package com.alkemy.ong.domain.utils;

import com.alkemy.ong.data.pagination.ModelPage;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import org.springframework.data.domain.Page;


public class DomainUtils {

    public static ModelPage generatePagination(Page pageEntity, String path){
        ModelPage page = new ModelPage();
        return setPagesNumbers(pageEntity.getNumber(), pageEntity,page,path);
    }

    public static ModelPage setPagesNumbers(Integer pageNumber, Page pageEntity,ModelPage modelPage, String path) {
        if ((pageNumber+1) <= pageEntity.getTotalPages()) {
            if (pageEntity.hasNext()) {
                modelPage.setNextPage(path.concat(String.valueOf(pageNumber + 1)));
            } else {
                modelPage.setNextPage("This is the last page");
            }
            if (pageEntity.hasPrevious()) {
                modelPage.setPreviuosPage(path.concat(String.valueOf(pageNumber - 1)));
            } else {
                modelPage.setPreviuosPage("This is the first page");
            }
        } else {
            throw new WebRequestException("The page does not exist");
        }
        modelPage.setModelList(pageEntity.getContent());
        return modelPage;
    }
}
