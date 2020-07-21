package com.eim.oauth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    public JwtResponse(String jwttoken) {
        this.token = jwttoken;
    }
}