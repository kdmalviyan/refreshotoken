package com.kd.pocs.refreshtoken.security.auth;

import com.kd.pocs.refreshtoken.user.User;
import com.kd.pocs.refreshtoken.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author kuldeep
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}
