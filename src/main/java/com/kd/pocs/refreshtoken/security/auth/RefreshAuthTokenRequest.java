package com.kd.pocs.refreshtoken.security.auth;

import lombok.Data;

/**
 * @author kuldeep
 */
@Data
public class RefreshAuthTokenRequest {
    private String refreshToken;
}
