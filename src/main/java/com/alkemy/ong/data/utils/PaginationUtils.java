package com.alkemy.ong.data.utils;

import com.alkemy.ong.data.pagination.PageModel;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import org.springframework.data.domain.Page;

public class PaginationUtils {

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static PageModel setPagesNumbers(Page pageEntity, String path) {
        PageModel modelPage = new PageModel();
        if ((pageEntity.getNumber()+1) <= pageEntity.getTotalPages()) {
            if (pageEntity.hasNext()) {
                modelPage.setNextPage(path.concat(String.valueOf(pageEntity.getNumber() + 1)));
            } else {
                modelPage.setNextPage("This is the last page");
            }
            if (pageEntity.hasPrevious()) {
                modelPage.setPreviousPage(path.concat(String.valueOf(pageEntity.getNumber() - 1)));
            } else {
                modelPage.setPreviousPage("This is the first page");
            }
        } else {
            throw new WebRequestException("The page does not exist");
        }
        modelPage.setBody(pageEntity.getContent());
        return modelPage;
    }
}
