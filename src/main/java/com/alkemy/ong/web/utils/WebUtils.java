package com.alkemy.ong.web.utils;

import com.alkemy.ong.domain.exceptions.WebRequestException;


public class WebUtils {
    public static void validateDtoIdWithBodyId(Long id, Long idDTO) {
        if(id!=idDTO){throw new WebRequestException("PathId does not match with RequestBody ID.");}
    }

    }