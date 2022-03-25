package com.alkemy.ong.domain.utils;

import com.alkemy.ong.data.pagination.ModelPage;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import org.springframework.data.domain.Page;


public class PaginationUtils {

    public static final Integer SIZE_REQUEST_PAGE = 10;

    public static ModelPage setPagesNumbers(Page pageEntity, String path) {
        ModelPage modelPage = new ModelPage();
        if ((pageEntity.getNumber()+1) <= pageEntity.getTotalPages()) {
            if (pageEntity.hasNext()) {
                modelPage.setNextPage(path.concat(String.valueOf(pageEntity.getNumber() + 1)));
            } else {
                modelPage.setNextPage("This is the last page");
            }
            if (pageEntity.hasPrevious()) {
                modelPage.setPreviuosPage(path.concat(String.valueOf(pageEntity.getNumber() - 1)));
            } else {
                modelPage.setPreviuosPage("This is the first page");
            }
        } else {
            throw new WebRequestException("The page does not exist");
        }
        modelPage.setBody(pageEntity.getContent());
        return modelPage;
    }
}
