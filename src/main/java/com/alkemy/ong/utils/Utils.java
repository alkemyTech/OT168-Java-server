package com.alkemy.ong.utils;

import java.util.Optional;

public class Utils {
    public static void validation(Long id, Long idDTO) {
        if(id==idDTO){
            optionalValidation(id==idDTO);
        }else{
            optionalValidation(null).orElseThrow();
        }
    }

    private static Optional<Boolean> optionalValidation (Boolean comp){
        Boolean b = Optional.ofNullable(comp).orElseThrow(NullPointerException::new);
        return Optional.ofNullable(b);
    }
}