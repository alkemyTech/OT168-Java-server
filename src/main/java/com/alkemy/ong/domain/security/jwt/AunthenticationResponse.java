package com.alkemy.ong.domain.security.jwt;

import lombok.Getter;

@Getter
public class AunthenticationResponse {

    private final String jwt;

    public AunthenticationResponse(String jwt){
        this.jwt=jwt;
    }
}
