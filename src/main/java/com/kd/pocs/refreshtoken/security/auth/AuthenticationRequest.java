package com.kd.pocs.refreshtoken.security.auth;

import lombok.Data;

/**
 * @author kuldeep
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}



