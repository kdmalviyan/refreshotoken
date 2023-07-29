package com.kd.pocs.refreshtoken.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kuldeep
 */
@Component
public class JwtTokenUtil {
    private String secret = "XH9zRjK9RDXzKAC4C3WcbUB3QaOnq1q9hY8wcdTtp19tBYKxfYXH9zRjK9RDXzKAC4C3WcbUB3QaOnq1q9hY8wcdTtp19tBYKxfYXH9zRjK9RDXzKAC4C3WcbUB3QaOnq1q9hY8wcdTtp19tBYKxfY";

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;
    private final SecretKey SECRET_KEY;

    public JwtTokenUtil() {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate Access Token
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenValidity);
    }

    // Generate Refresh Token
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", "refresh");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity * 1000))
                .signWith(SECRET_KEY)
                .compact();
        // return generateToken(userDetails, refreshTokenValidity);
    }

    private String generateToken(UserDetails userDetails, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", "general");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity * 1000))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && Objects.equals("general", getClaimValueFromToken("purpose", token)));
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (Objects.equals(username, userDetails.getUsername())
                && !isTokenExpired(token)
                && Objects.equals("refresh", getClaimValueFromToken("purpose", token)));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    private String getClaimValueFromToken(String name, String token) {
        return Objects.toString(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get(name));
    }
}
