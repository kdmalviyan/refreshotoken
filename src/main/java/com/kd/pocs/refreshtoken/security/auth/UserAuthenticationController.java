package com.kd.pocs.refreshtoken.security.auth;

import com.kd.pocs.refreshtoken.security.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author kuldeep
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("generate-token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody RefreshAuthTokenRequest refreshRequest) throws Exception {
        String refreshToken = refreshRequest.getRefreshToken();
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.validateRefreshToken(refreshToken, userDetails)) {
            final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
            refreshToken = jwtTokenUtil.generateRefreshToken(userDetails); //Replacing old refresh token with new
            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
        } else {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
