package com.alkemy.ong.web.utils;

import com.alkemy.ong.domain.exceptions.WebRequestException;


public class WebUtils {
    public static void validateDtoIdWithBodyId(Long id, Long idDTO) {
        if(id!=idDTO){throw new WebRequestException("PathId does not match with RequestBody ID.");}
    }

    public static void validatePageNumber(int numberPage){
        if(numberPage<0){
            throw new WebRequestException("The page number can not be lower a zero.");
        }
    }

    public static void validatePassword(String pswd1, String pswd2) {
        if(!pswd1.equals(pswd2)){throw new WebRequestException("The passwords don't match.");}
    }
}