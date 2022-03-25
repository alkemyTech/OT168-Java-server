package com.alkemy.ong.data.utils;

import com.alkemy.ong.data.GenericModelPage;
import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.members.Member;
import org.springframework.data.domain.Page;

import static java.util.stream.Collectors.toList;

public class DataUtils {

    public static GenericModelPage generatePagination(Page pageEntity, String path){
        GenericModelPage genericModelPage = new GenericModelPage();
        genericModelPage.setEntityPage(pageEntity);
       /* genericModelPage.setListModel(pageEntity
                .getContent()
                .stream()
                .map(this::toModel)
                .collect(toList()));*/
        setPagesNumbers(pageEntity.getNumber(), genericModelPage,path);
        return genericModelPage;
    }

    public static void setPagesNumbers(Integer pageNumber, GenericModelPage pageModel, String path) {
        if ((pageNumber+1) <= pageModel.getEntityPage().getTotalPages()) {
            if (pageModel.getEntityPage().hasNext()) {
                pageModel.setNextPage(path.concat(String.valueOf(pageNumber + 1)));
            } else {
                pageModel.setNextPage("This is the last page");
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
