package com.alkemy.ong.domain.pagination;

import com.alkemy.ong.domain.exceptions.WebRequestException;

public class Pagination {

    public static void findAll(Integer pageNumber, GenericModelPage pageModel, String path) {
        if (pageNumber <= (pageModel.getModelPage().getTotalPages() + 1)) {
            if (pageModel.getModelPage().hasNext()) {
                pageModel.setNextPage(path.concat(String.valueOf(pageNumber + 1)));
            } else {
                pageModel.setNextPage("This is the las page");
            }
            if (pageModel.getModelPage().hasPrevious()) {
                pageModel.setPreviousPage(path.concat(String.valueOf(pageNumber - 1)));
            } else {
                pageModel.setPreviousPage("This is the first page");
            }
        } else {
            throw new WebRequestException("The page does not exist");
        }
    }


}
