package com.kd.pocs.refreshtoken.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kuldeep
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}