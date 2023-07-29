package com.kd.pocs.refreshtoken.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kuldeep
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("all")
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
