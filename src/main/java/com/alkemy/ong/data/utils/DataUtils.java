package com.alkemy.ong.data.utils;

import com.alkemy.ong.data.GenericModelPage;
import com.alkemy.ong.domain.exceptions.WebRequestException;

public class DataUtils {

    public static void setPagesNumbers(Integer pageNumber, GenericModelPage pageModel, String path) {
        if ((pageNumber+1) <= pageModel.getEntityPage().getTotalPages()) {
            if (pageModel.getEntityPage().hasNext()) {
                pageModel.setNextPage(path.concat(String.valueOf(pageNumber + 1)));
            } else {
                pageModel.setNextPage("This is the las page");
            }
            if (pageModel.getEntityPage().hasPrevious()) {
                pageModel.setPreviousPage(path.concat(String.valueOf(pageNumber - 1)));
            } else {
                pageModel.setPreviousPage("This is the first page");
            }
        } else {
            throw new WebRequestException("The page does not exist");
        }
    }
}
